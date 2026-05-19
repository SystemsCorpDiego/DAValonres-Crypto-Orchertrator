package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.CrearDfaTokenPortIn;
import com.davalores.crypto.orchestrator.domain.model.DfaToken;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DfaTokenDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuarios/dfa")
public class CrearDFATokenController {

	private final CrearDfaTokenPortIn portIn;
	private final DfaTokenMapper mapper;

	public CrearDFATokenController(CrearDfaTokenPortIn portIn, DfaTokenMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	@PostMapping()
	public ResponseEntity<DfaTokenDto> run(HttpServletRequest request) {

		Integer usuarioId = 1; //TODO: recuperar usuarioId del token del header Authorization
		
		DfaToken dfaToken = portIn.run(usuarioId);
		DfaTokenDto response = mapper.run(dfaToken);
		
		return ResponseEntity.ok(response);
	}

}
