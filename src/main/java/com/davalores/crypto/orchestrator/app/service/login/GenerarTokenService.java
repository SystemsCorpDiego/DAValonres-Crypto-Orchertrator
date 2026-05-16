package com.davalores.crypto.orchestrator.app.service.login;

import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;

public interface GenerarTokenService {
	
	JWTokenBo run(String usuarioId, String username);
	JWTokenBo runParcial(String usuarioId, String username);
	
}
