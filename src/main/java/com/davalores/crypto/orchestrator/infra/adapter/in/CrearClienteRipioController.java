package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CrearClienteRipioPortIn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuarios/{usuarioId}/crypto-providers/ripio/clientes")
public class CrearClienteRipioController {

	private final CrearClienteRipioPortIn portIn;
	
	public CrearClienteRipioController(CrearClienteRipioPortIn portIn) {
		this.portIn = portIn;
	}
	
	@PostMapping
	public ResponseEntity<?> run(@PathVariable("usuarioId")  Integer usuarioId) {
        log.debug("run -> usuarioId: {}", usuarioId);
        
        portIn.run(usuarioId);
        
        log.debug("run -> return: null");
        return ResponseEntity.ok(null);
	}
	
}
