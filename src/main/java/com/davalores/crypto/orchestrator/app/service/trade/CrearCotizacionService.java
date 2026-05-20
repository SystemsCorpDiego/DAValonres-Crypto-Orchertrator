package com.davalores.crypto.orchestrator.app.service.trade;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearCotizacionPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CrearCotizacionPortOut;
import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearCotizacionService implements CrearCotizacionPortIn {

	
	private final CrearCotizacionPortOut portOut;
	
	public CrearCotizacionService(CrearCotizacionPortOut portOut) {
		this.portOut = portOut;
	}
	
	@Override
	public Cotizacion run(CotizacionSolicitud dto) {
		// TODO Auto-generated method stub
		log.debug("run -> dto: {}", dto);
		
		Cotizacion cotizacion = portOut.run(dto);
		
		log.debug("return -> cotizacion: {}", cotizacion);
		return cotizacion;
		
	}
	
}
