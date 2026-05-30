package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.app.port.out.dto.CrearComprobantePagoEscoDto;

public interface CrearComprobantePagoEscoPortOut {

	public Long run(CrearComprobantePagoEscoDto dto);
	
}
