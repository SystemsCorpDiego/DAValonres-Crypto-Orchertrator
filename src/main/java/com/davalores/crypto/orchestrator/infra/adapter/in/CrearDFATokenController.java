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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuarios/dfa")
public class CrearDFATokenController {

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
	
	@PostMapping()
	public ResponseEntity<DfaTokenDto> run(HttpServletRequest request) {

		Integer usuarioId = 1; //TODO: recuperar usuarioId del token del header Authorization
		
		String token = getAuthToken(request);
		
		DfaToken dfaToken = portIn.run(token);
		DfaTokenDto response = mapper.run(dfaToken);
		
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
