package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.esco.GetSaldoCuentaEscoPortIn;
import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.SaldoEscoDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.SaldoCuentaMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/esco/saldo")
public class GetSaldoCuentaEscoController {

	private final String tokenHeader;
	private final GetSaldoCuentaEscoPortIn portIn;
	private final SaldoCuentaMapper mapper;
	
	public GetSaldoCuentaEscoController(GetSaldoCuentaEscoPortIn portIn,
			@Value("${login.token.header}") String tokenHeader,
			SaldoCuentaMapper mapper) {
		this.tokenHeader = tokenHeader;
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	
	@GetMapping
	public ResponseEntity<SaldoEscoDto> run(HttpServletRequest request) {
		log.debug("inputParam -> NULL");
		SaldoEscoDto response = null;
		
		String token = getAuthToken(request);
		SaldoCuentaEsco dto = portIn.run(token);
		response = mapper.run(dto);
		
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
