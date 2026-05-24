package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.HabilitarRipioUsuarioPortIn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuarios/{usuarioId}/crypto-providers/ripio")
public class HabilitarRipioUsuarioController {

	private final HabilitarRipioUsuarioPortIn portIn;
	
	public HabilitarRipioUsuarioController(HabilitarRipioUsuarioPortIn portIn) {
		this.portIn = portIn;
	}
	
	@PutMapping("/habilitar")
	public ResponseEntity<?> runHabilitar(@PathVariable("usuarioId")  Integer usuarioId) {
        log.debug("inputParam -> usuarioId: {}", usuarioId);
        
        portIn.run(usuarioId, true);
        
        return ResponseEntity.ok(null);
	}
	
	@PutMapping("/deshabilitar")
	public ResponseEntity<?> runDeshabilitar(@PathVariable("usuarioId")  Integer usuarioId) {
        log.debug("inputParam -> usuarioId: {}", usuarioId);
        
        portIn.run(usuarioId, false);
        
        return ResponseEntity.ok(null);
	}
	
}
