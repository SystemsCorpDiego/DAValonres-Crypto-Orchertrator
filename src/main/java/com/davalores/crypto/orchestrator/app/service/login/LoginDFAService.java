package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginDFAPortIn;
import com.davalores.crypto.orchestrator.app.service.common.dfa.ValidarDFACode;
import com.davalores.crypto.orchestrator.app.service.common.jwt.GenerarAuthJwtToken;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginDFAService implements LoginDFAPortIn {

	private final ValidarDFACode validateDFACode;
	private final GenerarAuthJwtToken generarAuthToken;
	
	
	public LoginDFAService(ValidarDFACode validateDFACode,
			GenerarAuthJwtToken generarAuthToken) {
		this.validateDFACode = validateDFACode;
		this.generarAuthToken = generarAuthToken;
	}
	
	@Override
	public JWTokenBo run(String token, String dfaCode) {
		JWTokenBo salida = null;
		String dfaSeed = "";
		String usuario = "";
		Integer usuarioId = null;
		//token: validar q sea tipo:  TokenTipoEnum.PARCIAL
		//token: recuperar usuario 
		//Store: recuperar seed
		//validar dfaCode con seed
		
		if (validateDFACode.run(dfaSeed, dfaCode)) {			
			// generar token definitivo
			salida = generarAuthToken.run(usuarioId, usuario);			
		}

		return salida;
	}

}
