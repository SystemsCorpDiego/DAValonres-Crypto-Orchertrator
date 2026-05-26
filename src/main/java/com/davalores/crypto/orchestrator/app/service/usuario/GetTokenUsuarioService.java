package com.davalores.crypto.orchestrator.app.service.usuario;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JwtTokenData;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginDfaException;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;

@Service
public class GetTokenUsuarioService {
	
	private String secreto;
	private final UsuarioRepositoryPortOut usuarioRepository;
	
	public GetTokenUsuarioService(UsuarioRepositoryPortOut usuarioRepository,
			@Value("${login.token.secreto}") String secreto ) {
		super();
		this.secreto = secreto;
		this.usuarioRepository = usuarioRepository;
	}


	public Usuario run(String token, TokenTipoEnum tokenTipo) {
		//Valida que sea TokenTipoEnum.AUTENTICACION_PARCIAL y recupera el usuarioId del claim
		Optional<JwtTokenData> jwtTokenData = JWTServicePortOut.parseToken(token, secreto, tokenTipo);		
		if ( !jwtTokenData.isPresent() ) {
			if ( tokenTipo.equals(TokenTipoEnum.AUTENTICACION_PARCIAL) )
				throw new LoginDfaException(ErrorCodeEnum.HTTP_DFA_UNAUTHORIZED_ERROR.toString(), "Token invalido (1)");
			throw new LoginException(ErrorCodeEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "Token invalido (1)");
		}
		
		//recupera el usuarioId del repository
		Optional<Usuario> usuario = usuarioRepository.findById(jwtTokenData.get().usuarioId);
		if ( usuario.isEmpty() ) {
			if ( tokenTipo.equals(TokenTipoEnum.AUTENTICACION_PARCIAL) )
				throw new LoginDfaException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario con id mal configurado. Token invalido (2)");
			throw new LoginException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario con id mal configurado. Token invalido (2)");
		}
		return usuario.get();
	}
	
}
