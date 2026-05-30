package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.CrearDfaTokenPortIn;
import com.davalores.crypto.orchestrator.domain.model.DfaToken;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DfaTokenDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.DfaTokenMapper;
import com.davalores.crypto.orchestrator.infra.service.SessionLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "2) Administración de Usuarios", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/usuarios/dfa/semilla")
public class CrearDFATokenController {
	
	/*
	 * Crea una semilla de DFA en formato QR y URL para el usuario logueado. 
	 * 
	 */
	private final SessionLoginService sessionLoginService;
	private final CrearDfaTokenPortIn portIn;
	private final DfaTokenMapper mapper;

	public CrearDFATokenController(CrearDfaTokenPortIn portIn, 
			DfaTokenMapper mapper,
			SessionLoginService sessionLoginService) {
		this.portIn = portIn;
		this.mapper = mapper;
		this.sessionLoginService = sessionLoginService;
	}
	
	
	@Operation(summary = "Crea una semilla para login 2FA y la asocia al Usuario logueado." )	

	@PostMapping()
	public ResponseEntity<DfaTokenDto> run(HttpServletRequest request) {
		log.debug("inputParam -> ");
		
		Usuario usuarioLogin = sessionLoginService.getUsuario(request);
		log.debug("run -> {}", usuarioLogin);		
		
		DfaToken dfaToken = portIn.run(usuarioLogin.getId());
		DfaTokenDto response = mapper.run(dfaToken);
		
		log.debug("outputParam -> {}", response);		
		return ResponseEntity.ok(response);
	}

	 
	
}
