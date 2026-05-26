package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;

public interface GetSaldoCuentaEscoPortOut {

	public SaldoCuentaEsco run(String token, String comitente); 
	
}
