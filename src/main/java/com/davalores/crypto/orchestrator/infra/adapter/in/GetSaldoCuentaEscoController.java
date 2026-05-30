package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.esco.GetSaldoCuentaEscoPortIn;
import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.SaldoEscoDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.SaldoCuentaMapper;
import com.davalores.crypto.orchestrator.infra.service.SessionLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "3) Gestión", description = " ")
@SecurityRequirement(name = "basicAuth")


@Slf4j
@RestController
@RequestMapping("/esco/saldo")
public class GetSaldoCuentaEscoController {

	private final SessionLoginService sessionLoginService;
	private final GetSaldoCuentaEscoPortIn portIn;
	private final SaldoCuentaMapper mapper;
	
	public GetSaldoCuentaEscoController(GetSaldoCuentaEscoPortIn portIn,
			SessionLoginService sessionLoginService,
			SaldoCuentaMapper mapper) {
		this.sessionLoginService = sessionLoginService;
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	
	@Operation(summary = "Consulta de Saldo ESCO del usuario logueado" )	

	@GetMapping
	public ResponseEntity<SaldoEscoDto> run(HttpServletRequest request) {
		log.debug("inputParam -> NULL");
		SaldoEscoDto response = null;
		
		Usuario usuarioLogin = sessionLoginService.getUsuario(request);
		SaldoCuentaEsco dto = portIn.run(usuarioLogin);
		response = mapper.run(dto);
		
		log.debug("outputParam -> {}", response);
		return ResponseEntity.ok(response);
	}
	
	
}
