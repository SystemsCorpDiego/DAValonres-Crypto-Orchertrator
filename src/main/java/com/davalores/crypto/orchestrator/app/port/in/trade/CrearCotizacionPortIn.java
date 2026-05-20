package com.davalores.crypto.orchestrator.app.port.in.trade;

import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;

public interface CrearCotizacionPortIn {

	public Cotizacion run(CotizacionSolicitud dto);
	
}
