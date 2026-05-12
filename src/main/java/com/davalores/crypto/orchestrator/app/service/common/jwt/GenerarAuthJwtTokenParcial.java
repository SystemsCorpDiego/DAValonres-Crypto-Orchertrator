package com.davalores.crypto.orchestrator.app.service.common.jwt;

public interface GenerarAuthJwtTokenParcial {
	
	JWTokenBo run(Integer userId, String username);
	
}
