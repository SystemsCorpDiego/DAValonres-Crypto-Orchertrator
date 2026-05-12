package com.davalores.crypto.orchestrator.app.port.in.login;

import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;

public interface LoginDFAPortIn {

	public JWTokenBo run(String token, String dfaCode);
	
}
