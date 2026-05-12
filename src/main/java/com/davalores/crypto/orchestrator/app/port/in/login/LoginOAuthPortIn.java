package com.davalores.crypto.orchestrator.app.port.in.login;

import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;

public interface LoginOAuthPortIn {
	
	public JWTokenBo run(String usuario, String clave);
	
}
