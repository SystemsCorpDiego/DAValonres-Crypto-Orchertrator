package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.ConfirmarDFATokenPortIn;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
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
@RequestMapping("/usuarios/dfa")
public class ConfirmarDFATokenController {

	/*
	 * Controller para setear usuario.dfa = true o false. 
	 * Debe de tener una semilla creada 
	 * */
	
	private final SessionLoginService sessionLoginService;
	private final ConfirmarDFATokenPortIn portIn;
	
	public ConfirmarDFATokenController(ConfirmarDFATokenPortIn portIn,
			SessionLoginService sessionLoginService) {
		this.portIn = portIn;
		this.sessionLoginService = sessionLoginService;
	}
	
	
	
	@Operation(summary = "El Usuario logueado confirma la habilitación de la semilla 2FA previamente solicida. Esta acción activa el login con 2FA para el Usuario." )	

	@PutMapping("/habilitar")
	public ResponseEntity<?> habilitar(HttpServletRequest request) {		
		log.debug("inputParam -> ");
		
		Usuario usuarioLogin = sessionLoginService.getUsuario(request);		
		log.debug("run -> usuarioLogin: {}", usuarioLogin);

		portIn.run(usuarioLogin.getId(), false);
		
		log.debug("outputParam -> return: null");
		return ResponseEntity.ok(null);
	}

	@PutMapping("/deshabilitar")
	public ResponseEntity<?> deshabilitar(HttpServletRequest request) {
		log.debug("run -> ");

		Usuario usuarioLogin = sessionLoginService.getUsuario(request);		
		log.debug("run -> usuarioLogin: {}", usuarioLogin);
		
		portIn.run(usuarioLogin.getId(), true);
		
		log.debug("run -> return: null");
		return ResponseEntity.ok(null);
	}
	
}
