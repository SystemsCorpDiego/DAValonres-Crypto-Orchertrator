package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.CrearDfaTokenPortIn;
import com.davalores.crypto.orchestrator.domain.model.DfaToken;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DfaTokenDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.DfaTokenMapper;

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

	private final String tokenHeader;
	private final CrearDfaTokenPortIn portIn;
	private final DfaTokenMapper mapper;

	public CrearDFATokenController(CrearDfaTokenPortIn portIn, 
			DfaTokenMapper mapper,
			@Value("${login.token.header}") String tokenHeader) {
		this.portIn = portIn;
		this.mapper = mapper;
		this.tokenHeader = tokenHeader;
	}
	
	
	@Operation(summary = "Crea una semilla para login 2FA y la asocia al Usuario logueado." )	

	@PostMapping()
	public ResponseEntity<DfaTokenDto> run(HttpServletRequest request) {
		log.debug("inputParam -> ");
		
		String token = getAuthToken(request);
		log.debug("run -> {}", token);		
		
		DfaToken dfaToken = portIn.run(token);
		DfaTokenDto response = mapper.run(dfaToken);
		
		log.debug("outputParam -> {}", response);		
		return ResponseEntity.ok(response);
	}

	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader( tokenHeader );
		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		
		return token;
		
	}
	
}
