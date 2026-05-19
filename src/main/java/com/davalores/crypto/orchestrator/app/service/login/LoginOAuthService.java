package com.davalores.crypto.orchestrator.app.service.login;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthPortIn;
import com.davalores.crypto.orchestrator.app.port.out.EncriptadorClaveServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class LoginOAuthService implements LoginOAuthPortIn {
	//Este es como el LoginJWTImpl
	
	private final LoginEscoBolsaPortOut loginEscoBolsa;
	private final UsuarioRepositoryPortOut usuarioRepository;
	private final GenerarTokenService generarToken;
	private final EncriptadorClaveServicePortOut encriptadorClaveService;
	
	public LoginOAuthService(LoginEscoBolsaPortOut loginEscoBolsa, UsuarioRepositoryPortOut usuarioRepository,
			GenerarTokenService generarToken, 
			EncriptadorClaveServicePortOut encriptadorClaveService) {
		this.loginEscoBolsa = loginEscoBolsa;
		this.usuarioRepository = usuarioRepository;
		this.generarToken = generarToken;
		this.encriptadorClaveService = encriptadorClaveService;
	}
	
	@Override
	public JWTokenBo run(String usuaDescrip, String clave) {
		log.debug("Input parameter -> usuaDescrip: {}", usuaDescrip);
		Optional<JWTokenBo> token = Optional.empty();
		
		if ( usuaDescrip == null)
			throw new LoginException(null, "Debe informar un Usuario");
		if ( clave == null)
			throw new LoginException(null, "Debe informar un clave");
		
		Optional<Usuario> usuario = usuarioRepository.getByUsuario(usuaDescrip);

		// 1) login al middleware
		if (loginMW(usuario, clave)) {
			token = generoToken(usuario, clave);
			log.debug("login middleware - OK");
		} else {
			log.debug("login middleware - FAIL");
		}
		
		if ( usuario.isPresent() && token.isPresent() ) {
			log.debug("login middleware - Output parameter -> token {}", token.get());
			return token.get();
		}
		 
		
		// 2) login a esco
		UsuarioEsco usuarioEsco = null;
		try {
			usuarioEsco = loginEscoBolsa.run(usuaDescrip, clave);
		} catch (Exception e) {
            log.error("Error al loguear a VisualBolsa: " + e.toString());
            usuarioEsco = null;
		}
		
		if ( usuarioEsco!=null ) {
			if ( usuario.isPresent() ) {
				if ( usuario.get().getDfa() ) {
					token = Optional.of( generarToken.runParcial(usuario.get().getId().toString(), usuario.get().getDescripcion()) );
				} else {
					token = Optional.of( generarToken.run(usuarioEsco.getId(), clave) );
				}
			} else {
				log.debug("login ESCO - OK - Usuario MiddleWare Inexistente");
				token = Optional.of( generarToken.run(usuarioEsco.getId(), clave) );
			} 
		}
		
		if ( token.isPresent() ) {
			log.debug("login ESCO - Output parameter -> token {}", token.get());
			return token.get();
		}				
		
		throw new LoginException(null, "Usuario o clave invalidos");		
	}
	
	
	private Optional<JWTokenBo> generoToken(Optional<Usuario> usuario, String clave) {
		JWTokenBo token = null;
		if ( usuario.get().getDfa() ) {
			//genero token parcial		
			token = generarToken.runParcial(usuario.get().getId().toString(), usuario.get().getDescripcion());				
		} else {
			// genero token normal
			token = generarToken.run(usuario.get().getId().toString(), clave);
		}
		
		return Optional.of(token);
	}
	
	private boolean loginMW(Optional<Usuario> usuario, String clave) {
		if (usuario.isEmpty())
			return false;
			
		if (usuario.get().getClave() == null) {
			log.debug("Usuario {} sin clave en middleware", usuario.get().getDescripcion());
			return false;
		}
				
		if ( !encriptadorClaveService.validar(clave, usuario.get().getClave() ) ) {
			log.debug("loginMW - Usuario {} con clave INVALIDA en middleware", usuario.get().getDescripcion());
			return false;
		}
		
		return true;
	}
	
	
	
}
