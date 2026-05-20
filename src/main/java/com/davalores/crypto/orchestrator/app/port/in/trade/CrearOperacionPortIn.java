package com.davalores.crypto.orchestrator.app.port.in.trade;

import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;

public interface CrearOperacionPortIn {
	
	public Operacion run(String authToken, CrearOperacion dto);	

}
