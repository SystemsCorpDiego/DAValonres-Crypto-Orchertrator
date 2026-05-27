package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CrearClienteRipioPortIn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;



@Tag(name = "2) Administración de Usuarios", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/usuarios/{usuarioId}/providers/ripio/clientes")
public class CrearClienteRipioController {

	/*
	 *
	 * Api para crear un external_ref y asociarlo al usuario del Path-Parameter
	 * 
	 * 
	 */
	
	private final CrearClienteRipioPortIn portIn;
	
	public CrearClienteRipioController(CrearClienteRipioPortIn portIn) {
		this.portIn = portIn;
	}
	
	
	@Operation(summary = "Crea un Cliente Ripio para el usuario seleccionado en la URL." )	

	@PostMapping
	public ResponseEntity<?> run(@PathVariable("usuarioId")  Integer usuarioId) {
        log.debug("inputParam -> usuarioId: {}", usuarioId);
        
        portIn.run(usuarioId);
        
        log.debug("outputParam -> return: null");
        return ResponseEntity.ok(null);
	}
	
}
