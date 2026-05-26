package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;

public interface GetDetalleCuentaEscoPortOut {

	public DetalleCuentaEsco run(String token);
	
}
