package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.Operacion;

public interface OperacionRepositoryPortOut {

	public Operacion save(Operacion operacion);
	
}
