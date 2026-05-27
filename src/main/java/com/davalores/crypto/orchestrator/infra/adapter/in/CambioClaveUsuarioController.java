package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.usuario.CambioClaveUsuarioPortIn;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CambioClaveUsuarioDto;

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

	private final String tokenHeader; 
	private final CambioClaveUsuarioPortIn portIn;
	
	public CambioClaveUsuarioController(CambioClaveUsuarioPortIn portIn,
			@Value("${login.token.header}") String tokenHeader) {
		this.portIn = portIn;
		this.tokenHeader = tokenHeader;
	}
	
	
	@Operation(summary = "Gestiona la actualización de Clave para el Usuario indicado. " )	

	@PutMapping("/cambiar-clave")
	public ResponseEntity<?> run(HttpServletRequest request, @PathVariable("usuarioId")  Integer usuarioId, @RequestBody CambioClaveUsuarioDto dto) {
		log.debug("inputParam -> {}", dto);
		
		if ( usuarioId == null )
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un id de usuario");
		if ( dto == null || dto.getClave()==null || dto.getClaveNueva()==null )
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar la clave actual junto a una nueva");
		
		String token = getAuthToken(request);
		
		portIn.run(token, usuarioId, dto);
		
		log.debug("outputParam ->  null" );
		return ResponseEntity.ok(null);
	}
	
	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader( tokenHeader );
		if (auth == null)
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");
		

		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		if (token == null)
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");
				
		return token;	
	}
}
