package com.davalores.crypto.orchestrator.app.port.in.login;

import com.davalores.crypto.orchestrator.domain.model.DfaToken;

public interface CrearDfaTokenPortIn {

	public DfaToken run(Integer usuarioId);
	public DfaToken run(String token);
	
}
