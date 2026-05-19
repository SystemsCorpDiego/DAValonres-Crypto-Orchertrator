package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.ConfirmarDFATokenPortIn;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuarios/dfa")
public class ConfirmarDFATokenController {

	private final String tokenHeader;
	private final ConfirmarDFATokenPortIn portIn;
	
	public ConfirmarDFATokenController(ConfirmarDFATokenPortIn portIn,
			@Value("${login.token.header}") String tokenHeader) {
		this.portIn = portIn;
		this.tokenHeader = tokenHeader;
	}
	
	
	@PutMapping("/habilitar/FALSE")
	public ResponseEntity<?> habilitar(HttpServletRequest request) {
		log.debug("run -> ");
		String token = getAuthToken(request);
		log.debug("run -> token: {}", token);

		portIn.run(token, false);
		
		log.debug("run -> return: null");
		return ResponseEntity.ok(null);
	}

	@PutMapping("/habilitar/TRUE")
	public ResponseEntity<?> deshabilitar(HttpServletRequest request) {
		log.debug("run -> ");

		String token = getAuthToken(request);
		log.debug("run -> token: {}", token);
		
		portIn.run(token, true);
		
		log.debug("run -> return: null");
		return ResponseEntity.ok(null);
	}
	
	
	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader( tokenHeader );
		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		
		return token;		
	}
	
}
