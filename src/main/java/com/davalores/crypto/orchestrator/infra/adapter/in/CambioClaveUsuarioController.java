package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CambioClaveUsuarioPortIn;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CambioClaveUsuarioDto;
import com.davalores.crypto.orchestrator.infra.service.SessionLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "2) Administración de Usuarios", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("usuarios/{usuarioId}")
public class CambioClaveUsuarioController {

	private final SessionLoginService sessionLoginService;
	private final CambioClaveUsuarioPortIn portIn;
	
	public CambioClaveUsuarioController(CambioClaveUsuarioPortIn portIn,
			SessionLoginService sessionLoginService) {
		this.portIn = portIn;
		this.sessionLoginService = sessionLoginService;
	}
	
	
	@Operation(summary = "Gestiona la actualización de Clave para el Usuario indicado. " )	

	@PutMapping("/cambiar-clave")
	public ResponseEntity<?> run(HttpServletRequest request, @PathVariable("usuarioId")  Integer usuarioId, @RequestBody CambioClaveUsuarioDto dto) {
		log.debug("inputParam -> {}", dto);
		
		Usuario usuarioLogin = sessionLoginService.getUsuario(request);
		
		if ( usuarioId == null )
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un id de usuario");
		if ( dto == null || dto.getClave()==null || dto.getClaveNueva()==null )
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar la clave actual junto a una nueva");
		
		portIn.run(usuarioLogin, usuarioId, dto);
		
		log.debug("outputParam ->  null" );
		return ResponseEntity.ok(null);
	}
	
}
