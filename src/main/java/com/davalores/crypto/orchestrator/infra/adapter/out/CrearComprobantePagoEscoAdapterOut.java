package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.CrearComprobantePagoEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.dto.CrearComprobantePagoEscoDto;

@Service
public class CrearComprobantePagoEscoAdapterOut implements CrearComprobantePagoEscoPortOut {

	@Override
	public Long run(CrearComprobantePagoEscoDto dto) {
		// TODO Auto-generated method stub
		return 1L;
	}

}
