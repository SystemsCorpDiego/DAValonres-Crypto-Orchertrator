package com.davalores.crypto.orchestrator.app.port.in.esco;

import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;

public interface GetSaldoCuentaEscoPortIn {

	public SaldoCuentaEsco run(String token);
	
}
