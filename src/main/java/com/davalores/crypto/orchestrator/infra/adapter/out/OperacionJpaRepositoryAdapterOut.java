package com.davalores.crypto.orchestrator.infra.adapter.out;

import com.davalores.crypto.orchestrator.app.port.out.OperacionRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Operacion;

public class OperacionJpaRepositoryAdapterOut implements OperacionRepositoryPortOut {

	@Override
	public Operacion save(Operacion operacion) {
		Operacion operacionGuardada = null;
		
		return operacionGuardada;
	}
	
}
