package com.davalores.crypto.orchestrator.app.service.common.jwt;

public interface GenerarAuthJwtToken {
	
	public JWTokenBo run(Integer userId, String username);
	
}
