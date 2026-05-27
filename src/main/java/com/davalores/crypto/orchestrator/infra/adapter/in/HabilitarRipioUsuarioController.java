package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.HabilitarRipioUsuarioPortIn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "2) Administración de Usuarios", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/usuarios/{usuarioId}/providers/ripio")
public class HabilitarRipioUsuarioController {

	private final HabilitarRipioUsuarioPortIn portIn;
	
	public HabilitarRipioUsuarioController(HabilitarRipioUsuarioPortIn portIn) {
		this.portIn = portIn;
	}
	
	
	@Operation(summary = "Gestiona la habilitación del uso de Ripio" )	

	@PutMapping("/habilitar")
	public ResponseEntity<?> runHabilitar(@PathVariable("usuarioId")  Integer usuarioId) {
        log.debug("inputParam -> usuarioId: {}", usuarioId);
        
        portIn.run(usuarioId, true);
        
        return ResponseEntity.ok(null);
	}
	
	@Operation(summary = "Deshabilita el uso de Ripio para el usuario en cuestión" )

	@PutMapping("/deshabilitar")
	public ResponseEntity<?> runDeshabilitar(@PathVariable("usuarioId")  Integer usuarioId) {
        log.debug("inputParam -> usuarioId: {}", usuarioId);
        
        portIn.run(usuarioId, false);
        
        return ResponseEntity.ok(null);
	}
	
}
