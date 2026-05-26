package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginDFAPortIn;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.dfa.ValidarDFACode;
import com.davalores.crypto.orchestrator.app.service.common.jwt.GenerarAuthJwtToken;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.app.service.usuario.GetTokenUsuarioService;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginDfaException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginDFAService implements LoginDFAPortIn {

	private final ValidarDFACode validateDFACode;
	private final GenerarAuthJwtToken generarAuthToken;
	private final GetTokenUsuarioService getTokenUsuarioService;
	
	public LoginDFAService(ValidarDFACode validateDFACode,
			GenerarAuthJwtToken generarAuthToken, 
			UsuarioRepositoryPortOut usuarioRepository,
			GetTokenUsuarioService getTokenUsuarioService) {
		this.validateDFACode = validateDFACode;
		this.generarAuthToken = generarAuthToken;
		this.getTokenUsuarioService = getTokenUsuarioService;
	}
	
	@Override
	public JWTokenBo run(String token, String dfaCode) {
		log.debug("inputParam -> token: {} dfaCode: {}", token, dfaCode);
		JWTokenBo salida = null;

		Usuario usuario = getTokenUsuarioService.run(token, TokenTipoEnum.AUTENTICACION_PARCIAL);
		
		//valida que el dfaCode sea correcto para la semilla del usuario
		if ( !validateDFACode.run(usuario.getDfaSemilla(), dfaCode))
			throw new LoginDfaException(ErrorCodeEnum.HTTP_DFA_UNAUTHORIZED_ERROR.toString(), "Código de validación 2FA invalido");
		
		// generar token definitivo
		salida = generarAuthToken.run(usuario.getId(), usuario.getUsuario());			

		log.debug("outputParam: {}", salida);
		return salida;
	} 

}
