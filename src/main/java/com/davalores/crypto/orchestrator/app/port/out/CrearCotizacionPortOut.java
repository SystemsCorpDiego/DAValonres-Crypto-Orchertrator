package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;

public interface CrearCotizacionPortOut {

	public Cotizacion run(CotizacionSolicitud solicitud);
	
}
