package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;

public interface CrearOperacionRipioPortOut {

	public Operacion run(CrearOperacion dto);
	
}
