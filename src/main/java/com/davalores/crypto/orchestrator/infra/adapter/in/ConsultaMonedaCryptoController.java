package com.davalores.crypto.orchestrator.infra.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.trade.ConsultarMonedaCryptoPortIn;
import com.davalores.crypto.orchestrator.domain.model.MonedaCrypto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "4) Operación Crypto", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/providers/monedas")
public class ConsultaMonedaCryptoController {

	private final ConsultarMonedaCryptoPortIn portIn;
	
	public ConsultaMonedaCryptoController(ConsultarMonedaCryptoPortIn portIn) {
		this.portIn = portIn;
	}
	
	
	@Operation(summary = "Consulta de Monedas Crypto." )	

	@GetMapping
	public ResponseEntity<List<MonedaCrypto>> run() {
		log.debug("inputParam -> ");

		List<MonedaCrypto> respnse = portIn.run();
		
		log.debug("outputParam -> {}", respnse);
		return ResponseEntity.ok(respnse);
	}
	
}
