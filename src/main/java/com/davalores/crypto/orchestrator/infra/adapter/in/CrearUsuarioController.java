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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("usuarios/")
public class CrearUsuarioController {

	private final CrearUsuarioPortIn portIn;
	private final UsuarioMapper mapper;
	
	public CrearUsuarioController(CrearUsuarioPortIn portIn, UsuarioMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	@PostMapping
	public ResponseEntity<UsuarioDto> run(@RequestBody CrearUsuarioDto dto) {
		log.debug("run -> dto: {}", dto);
		
		Usuario usuario = mapper.run(dto);
		usuario = portIn.run(usuario);
		UsuarioDto response = mapper.run(usuario);
		
		log.debug("run -> response: {}", response);
		return ResponseEntity.ok(response);
	}
	
}
