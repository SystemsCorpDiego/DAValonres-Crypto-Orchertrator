package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.ConfirmarDFATokenPortIn;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuarios/dfa")
public class ConfirmarDFATokenController {

	private final ConfirmarDFATokenPortIn portIn;
	
	public ConfirmarDFATokenController(ConfirmarDFATokenPortIn portIn) {
		this.portIn = portIn;
	}
	
	
	@PutMapping("/habilitar/FALSE")
	public ResponseEntity<?> habilitar(HttpServletRequest request) {

		Integer usuarioId = 1; //TODO: recuperar usuarioId del token del header Authorization
		
		portIn.run(usuarioId, false);
		
		return ResponseEntity.ok(null);
	}

	@PutMapping("/habilitar/TRUE")
	public ResponseEntity<?> deshabilitar(HttpServletRequest request) {

		Integer usuarioId = 1; //TODO: recuperar usuarioId del token del header Authorization
		
		portIn.run(usuarioId, true);
		
		return ResponseEntity.ok(null);
	}
	
}
