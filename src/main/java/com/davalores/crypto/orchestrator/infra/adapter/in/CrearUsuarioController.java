package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CrearUsuarioPortIn;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearUsuarioDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.UsuarioDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "2) Administración de Usuarios", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("usuarios/")
public class CrearUsuarioController {

	/*
	 * Crea un usuario en la base de Crypto-Orchestrator
	 */
	
	private final CrearUsuarioPortIn portIn;
	private final UsuarioMapper mapper;
	
	public CrearUsuarioController(CrearUsuarioPortIn portIn, UsuarioMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	
	@Operation(summary = "Crea Usuario Middleware. Estos usuario no pertenecen a ESCO y por lo tanto no están habilitados a operar con Ripio." )	

	@PostMapping
	public ResponseEntity<UsuarioDto> run(@RequestBody CrearUsuarioDto dto) {
		log.debug("inputParam -> {}", dto);
		
		Usuario usuario = mapper.run(dto);
		usuario = portIn.run(usuario);
		UsuarioDto response = mapper.run(usuario);
		
		log.debug("outputParam ->  {}", response);
		return ResponseEntity.ok(response);
	}
	
}
