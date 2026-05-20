package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearCotizacionPortIn;
import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CotizacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearCotizacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.CotizacionMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/crypto-providers/cotizar")
public class CrearCotizacionController {

	private final CrearCotizacionPortIn portIn;
	private final CotizacionMapper mapper;
	
	public CrearCotizacionController(CrearCotizacionPortIn portIn, CotizacionMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	@GetMapping
	public ResponseEntity<CotizacionDto> run(@RequestBody CrearCotizacionDto dto) {
		
		CotizacionSolicitud cotizacionSolicitud = mapper.run(dto);
		Cotizacion cotizacion = portIn.run(cotizacionSolicitud);
		CotizacionDto response = mapper.run(cotizacion);
		
		return ResponseEntity.ok(response);
	}
	
}
