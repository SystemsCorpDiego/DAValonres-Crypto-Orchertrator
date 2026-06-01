package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.Cotizacion;

public interface GetCotizacionRipioPortOut {

	public Cotizacion run(String cotizacionId);
	
}
