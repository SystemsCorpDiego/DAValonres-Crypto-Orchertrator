package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearOperacionPortIn;
import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearOperacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.OperacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.OperacionMapper;
import com.davalores.crypto.orchestrator.infra.service.SessionLoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Tag(name = "4) Operación Crypto", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/providers/operacion")
public class CrearOperacionController {

	private final SessionLoginService sessionLoginService;
	private final CrearOperacionPortIn portIn;
	private final OperacionMapper mapper;
	
	public CrearOperacionController(
			SessionLoginService sessionLoginService, CrearOperacionPortIn portIn, OperacionMapper mapper) {
		this.sessionLoginService = sessionLoginService;
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	
	@Operation(summary = "Crea una Operación de Compra en Ripio para el usuario logueado utilizando la cotización indicada en el json-body del request." )	

	@PostMapping("/compra")
	public ResponseEntity<?> runCompra(HttpServletRequest request, @RequestBody CrearOperacionDto dto) {
		if ( dto == null )
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "El cuerpo de la solicitud no puede ser nulo");

		dto.setTipo("COMPRA");
		return this.run(request, dto);
	}
	

	@Operation(summary = "Crea una Operación de Venta en Ripio para el usuario logueado utilizando la cotización indicada en el json-body del request." )	

	@PostMapping("/venta")
	public ResponseEntity<?> runVenta(HttpServletRequest request, @RequestBody CrearOperacionDto dto) {
		if ( dto == null )
			throw new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "El cuerpo de la solicitud no puede ser nulo");

		dto.setTipo("VENTA");
		return this.run(request, dto);
	}
	
	private ResponseEntity<OperacionDto> run(HttpServletRequest request, CrearOperacionDto dto ) {
		log.debug("input -> {}", dto);
		
		if ( dto.getCantidad() == null )
			throw new BusinessException("Debe indicar una cantidad");
		if ( dto.getIdExternoProveedorCotizacion() == null )
			throw new BusinessException("Debe indicar un id de cotización");
		
		Usuario usuarioLogin = sessionLoginService.getUsuario(request);
		
		CrearOperacion reg = mapper.run(dto);		
		Operacion operacion = portIn.run( usuarioLogin, reg );		
		OperacionDto response = mapper.run(operacion);
		
		log.debug("output -> {}", response);
		return ResponseEntity.ok(response);
	}
	
	
}
