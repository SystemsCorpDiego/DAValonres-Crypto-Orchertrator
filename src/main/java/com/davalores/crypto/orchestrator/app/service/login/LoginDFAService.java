package com.davalores.crypto.orchestrator.app.service.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginDFAPortIn;
import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.dfa.ValidarDFACode;
import com.davalores.crypto.orchestrator.app.service.common.jwt.GenerarAuthJwtToken;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JwtTokenData;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginDfaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginDFAService implements LoginDFAPortIn {

	private final String secreto;
	private final ValidarDFACode validateDFACode;
	private final GenerarAuthJwtToken generarAuthToken;
	private final UsuarioRepositoryPortOut usuarioRepository;
	
	public LoginDFAService(ValidarDFACode validateDFACode,
			GenerarAuthJwtToken generarAuthToken, 
			@Value("${login.token.secreto}") String secreto, 
			UsuarioRepositoryPortOut usuarioRepository) {
		this.secreto = secreto;
		this.validateDFACode = validateDFACode;
		this.generarAuthToken = generarAuthToken;
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public JWTokenBo run(String token, String dfaCode) {
		log.debug("inputParam -> token: {} dfaCode: {}", token, dfaCode);
		JWTokenBo salida = null;

		//Valida que sea TokenTipoEnum.AUTENTICACION_PARCIAL y recupera el usuarioId del claim
		Optional<JwtTokenData> jwtTokenData = JWTServicePortOut.parseToken(token, secreto, TokenTipoEnum.AUTENTICACION_PARCIAL);		
		if ( !jwtTokenData.isPresent() )
			throw new LoginDfaException(ErrorCodeEnum.HTTP_DFA_UNAUTHORIZED_ERROR.toString(), "Token invalido (1)");
		
		
		//recupera el usuarioId del repository
		Optional<Usuario> usuario = usuarioRepository.findById(jwtTokenData.get().usuarioId);
		if ( usuario.isEmpty() )
			throw new LoginDfaException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario con id mal configurado. Token invalido (2)");
		
		//valida que el dfaCode sea correcto para la semilla del usuario
		if ( !validateDFACode.run(usuario.get().getDfaSemilla(), dfaCode))
			throw new LoginDfaException(ErrorCodeEnum.HTTP_DFA_UNAUTHORIZED_ERROR.toString(), "Código de validación 2FA invalido");
		
		// generar token definitivo
		salida = generarAuthToken.run(usuario.get().getId(), usuario.get().getUsuario());			

		log.debug("outputParam: {}", salida);
		return salida;
	} 

}
