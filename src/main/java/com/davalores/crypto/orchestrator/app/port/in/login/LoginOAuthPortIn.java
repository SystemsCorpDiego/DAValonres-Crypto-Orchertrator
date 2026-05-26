package com.davalores.crypto.orchestrator.app.port.in.login;

import com.davalores.crypto.orchestrator.domain.model.LoginOauth;

public interface LoginOAuthPortIn {
	
	public LoginOauth run(String usuario, String clave);
	
}
