package com.davalores.crypto.orchestrator.infra.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.ConsultarUsuariosPortIn;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.UsuarioDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.UsuarioMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "2) Administración de Usuarios", description = " ")

@SecurityRequirement(name = "bearerAuth")

@Slf4j
@RestController
@RequestMapping("usuarios")
public class ConsultarUsuariosController {

	private final ConsultarUsuariosPortIn portIn;
	private final UsuarioMapper mapper;
		
	public ConsultarUsuariosController(ConsultarUsuariosPortIn portIn, UsuarioMapper mapper) {
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	
	@Operation(summary = "Consulta de Usuarios del MiddleWare." )	

	@GetMapping
	public ResponseEntity<List<UsuarioDto>> run() {
		log.debug("run -> ");
		List<Usuario> lst = portIn.run();
		List<UsuarioDto> lst2 = mapper.run(lst);
		log.debug("outputParam -> lst2: {}", lst2);
		return ResponseEntity.ok(lst2);
	}
	
}
