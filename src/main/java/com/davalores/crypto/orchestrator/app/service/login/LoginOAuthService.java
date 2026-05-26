package com.davalores.crypto.orchestrator.app.service.login;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthPortIn;
import com.davalores.crypto.orchestrator.app.port.out.EncriptadorClaveServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetDetalleCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.LoginOauth;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.infra.adapter.out.CypherServiceAdapterOut;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class LoginOAuthService implements LoginOAuthPortIn {
	//Este es como el LoginJWTImpl
	
	private final LoginEscoBolsaPortOut loginEscoBolsa;
	private final UsuarioRepositoryPortOut usuarioRepository;
	private final GenerarTokenService generarToken;
	private final GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut;
	private final EncriptadorClaveServicePortOut encriptadorClaveService;
	private final CypherServiceAdapterOut cypherServiceAdapterOut;
	
	public LoginOAuthService(LoginEscoBolsaPortOut loginEscoBolsa, UsuarioRepositoryPortOut usuarioRepository,
			GenerarTokenService generarToken, 
			EncriptadorClaveServicePortOut encriptadorClaveService, GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut, CypherServiceAdapterOut cypherServiceAdapterOut) {
		this.loginEscoBolsa = loginEscoBolsa;
		this.usuarioRepository = usuarioRepository;
		this.generarToken = generarToken;
		this.getDetalleCuentaEscoPortOut = getDetalleCuentaEscoPortOut;
		this.encriptadorClaveService = encriptadorClaveService;
		this.cypherServiceAdapterOut = cypherServiceAdapterOut;
	}
	
	@Override
	public LoginOauth run(String usuaDescrip, String clave) {
		//JWTokenBo 
		log.debug("InputParam -> usuaDescrip: {} clave: {}", usuaDescrip, clave);
		Optional<JWTokenBo> token = Optional.empty();
		LoginOauth loginOauth = null;
		
		if ( usuaDescrip == null)
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un Usuario");
		if ( clave == null)
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un Clave");
		

		//1) Busco usuario en MW
		Optional<Usuario> usuarioMW = usuarioRepository.findByUsuario(usuaDescrip);
		

		//2) Me logeo a ESCO
		UsuarioEsco usuarioEsco = null;		
		DetalleCuentaEsco detalleCuentaEsco = null;
		try {
			usuarioEsco = loginEscoBolsa.run(usuaDescrip, clave);
		} catch (Exception e) {
            log.error("Error al loguear a ESCO: " + e.toString());
            usuarioEsco = null;
		}

		if ( usuarioEsco != null ) {
			//3) Traigo Detalle Cuenta ESCO
			detalleCuentaEsco = getDetalleCuentaEscoPortOut.run( usuarioEsco.getAccessToken() );			
			
			if ( (usuarioEsco.getNombre() == null || usuarioEsco.getNombre().equals(usuaDescrip) )
					&& detalleCuentaEsco != null 
					&& detalleCuentaEsco.getComitenteDescripcion() != null ) {
				usuarioEsco.setNombre(detalleCuentaEsco.getComitenteDescripcion());
			}
			
			//4) Actualizo usuario en MW => alta/modi.-
			usuarioMW = actualizarUsuarioMW(usuarioMW, usuarioEsco);			
		}
		
		//5) Me logueo al MW 
		loginMW(usuarioMW, clave);
		
		//6) Genero token
		token = generoToken(usuarioMW);
		
		
		
		if ( usuarioEsco == null && usuarioMW.get().getEscoId() != null )
			throw new LoginException(ErrorCodeEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "Usuario o clave inválidos (4)");
		if ( usuarioMW.isEmpty())
			throw new LoginException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario o clave inválidos (5)");
		if ( token.isEmpty())
			throw new LoginException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario o clave inválidos (6)");
		
						
		loginOauth = new LoginOauth();
		loginOauth.setToken(token.get().getToken());
		loginOauth.setTokenRefresco(token.get().getTokenRefresco());
		loginOauth.setCuentaEsco(detalleCuentaEsco);
		
		log.debug("outputParam -> {}", loginOauth);		
		return loginOauth; 
	}
	
	
	private Optional<JWTokenBo> generoToken(Optional<Usuario> usuario) {
		//, String clave

		JWTokenBo token = null;
		if ( usuario.get().getDfa() ) {
			token = generarToken.runParcial(usuario.get().getId().toString(), usuario.get().getUsuario());				
		} else {
			token = generarToken.run(usuario.get().getId().toString(), usuario.get().getUsuario());
		}
		
		return Optional.of(token);
	}
	
	private void loginMW(Optional<Usuario> usuario, String clave) {		
		if (usuario.isEmpty())
			throw new LoginException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario o clave inválidos (1)");
		
		if (!usuario.get().getHabilitado())
			throw new LoginException(ErrorCodeEnum.BUSINESS_ERROR.toString(), "Su cuenta de Usuario se encuentra deshabilitada. Por favor, contacte al administrador del sistema.");

		
		//Si no es Usuario ESCO, valido clave
		if ( usuario.get().getEscoId() == null  ) {
			if (usuario.get().getClave() == null ) {
				throw new LoginException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario o clave inválidos (2)");
			}
			
			if ( !encriptadorClaveService.validar(clave, usuario.get().getClave() ) ) {
				log.debug("loginMW - Usuario {} con clave INVALIDA en middleware", usuario.get().getUsuario());
				throw new LoginException(ErrorCodeEnum.BUSINESS_ERROR.toString(), "Usuario o clave inválidos (3)");
			}
		}
	}
	
	
	private Optional<Usuario> actualizarUsuarioMW(Optional<Usuario> usuario, UsuarioEsco usuarioEsco ) {
		Usuario usuarioNew;

		if ( usuario.isEmpty() ) {
			usuarioNew = new Usuario();
			usuarioNew.setEscoId(usuarioEsco.getId());
			usuarioNew.setUsuario(usuarioEsco.getUsuario());
			usuarioNew.setDescripcion(usuarioEsco.getNombre()); //TODO: usar api ESCO de cuenta bancaria 
			usuarioNew.setClaveEsco(cypherServiceAdapterOut.encriptar(usuarioEsco.getClave()));			
			
			usuarioNew.setHabilitado(true);
			usuarioNew.setDfa(false);
			usuarioNew.setDfaSemilla(null);
			usuarioNew.setRipioHabilitado(true);
			usuarioNew.setRipioId(null);

		} else {
			usuarioNew = usuario.get();
			usuarioNew.setEscoId(usuarioEsco.getId());
			usuarioNew.setUsuario(usuarioEsco.getUsuario()); 
			usuarioNew.setClaveEsco(cypherServiceAdapterOut.encriptar(usuarioEsco.getClave()));			
		}
		
		usuarioNew = usuarioRepository.save(usuarioNew);
		
		return Optional.of(usuarioNew);		
	}
	
}
