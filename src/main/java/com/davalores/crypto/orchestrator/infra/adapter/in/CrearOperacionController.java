package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearOperacionPortIn;
import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCoreEnum;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearOperacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.OperacionMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/crypto-providers/operacion")
public class CrearOperacionController {

	private final String tokenHeader;
	private final CrearOperacionPortIn portIn;
	private final OperacionMapper mapper;
	
	public CrearOperacionController(
			@Value("${login.token.header}") String tokenHeader, CrearOperacionPortIn portIn, OperacionMapper mapper) {
		this.tokenHeader = tokenHeader;
		this.portIn = portIn;
		this.mapper = mapper;
	}
	
	@PostMapping("COMPRA")
	public ResponseEntity<?> runCompra(HttpServletRequest request, @RequestBody CrearOperacionDto dto) {
		if ( dto == null )
			throw new BusinessException(ErrorCoreEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "El cuerpo de la solicitud no puede ser nulo");

		dto.setTipo("COMPRA");
		return this.run(request, dto);
	}
	
	@PostMapping("VENTA")
	public ResponseEntity<?> runVenta(HttpServletRequest request, @RequestBody CrearOperacionDto dto) {
		if ( dto == null )
			throw new BusinessException(ErrorCoreEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "El cuerpo de la solicitud no puede ser nulo");

		dto.setTipo("VENTA");
		return this.run(request, dto);
	}
	
	private ResponseEntity<?> run(HttpServletRequest request, CrearOperacionDto dto ) {
		log.debug("inputParam -> {}", dto);
		
		if ( dto.getCantidad() == null )
			throw new BusinessException("Debe indicar una cantidad");
		if ( dto.getIdExternoProveedorCotizacion() == null )
			throw new BusinessException("Debe indicar un id de cotización");
		
		String authToken = getAuthToken(request);
		
		CrearOperacion reg = mapper.run(dto);
		
		portIn.run( authToken, reg );
		
		log.debug("outParam -> null");
		return ResponseEntity.ok(null);
	}
	
	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader( tokenHeader );
		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		
		return token;		
	}
	
}
