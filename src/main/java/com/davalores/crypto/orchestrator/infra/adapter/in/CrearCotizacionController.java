package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearCotizacionPortIn;
import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CotizacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearCotizacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.CotizacionMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "4) Operación Crypto", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/providers/cotizar")
public class CrearCotizacionController {

	
	private final CrearCotizacionPortIn portIn;
	private final CotizacionMapper mapper;
	
	public CrearCotizacionController(CrearCotizacionPortIn portIn, CotizacionMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	
	@Operation(summary = "Crea una Cotización Ripio para la moneda indicada." )	

	@PostMapping
	public ResponseEntity<CotizacionDto> run(@RequestBody CrearCotizacionDto dto) {
		log.debug("inputParam -> {}", dto);
		CotizacionSolicitud cotizacionSolicitud = mapper.run(dto);
		Cotizacion cotizacion = portIn.run(cotizacionSolicitud);
		CotizacionDto response = mapper.run(cotizacion);
		
		log.debug("outputParam -> {}", response);
		return ResponseEntity.ok(response);
	}
	
}
