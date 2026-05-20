package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearOperacionDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/crypto-providers/operacion")
public class CrearOperacionController {

	private final String tokenHeader;
	
	public CrearOperacionController(
			@Value("${login.token.header}") String tokenHeader) {
		this.tokenHeader = tokenHeader;
	}
	
	@PostMapping("COMPRA")
	public void runCompra(HttpServletRequest request, @RequestBody CrearOperacionDto dto) {
		if ( dto == null )
			throw new BusinessException("El cuerpo de la solicitud no puede ser nulo");

		dto.setTipoOperacion("COMPRA");
		this.run(request, dto);
	}
	
	@PostMapping("VENTA")
	public void runVenta(HttpServletRequest request, @RequestBody CrearOperacionDto dto) {
		if ( dto == null )
			throw new BusinessException("El cuerpo de la solicitud no puede ser nulo");

		dto.setTipoOperacion("VENTA");
		this.run(request, dto);
	}
	
	private void run(HttpServletRequest request, CrearOperacionDto dto ) {
		if ( dto.getCantidad() == null )
			throw new BusinessException("Debe indicar una cantidad");
		if ( dto.getIdExternoProveedorCotizacion() == null )
			throw new BusinessException("Debe indicar un id de cotización");
		
		
	}
	
	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader( tokenHeader );
		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		
		return token;		
	}
	
}
