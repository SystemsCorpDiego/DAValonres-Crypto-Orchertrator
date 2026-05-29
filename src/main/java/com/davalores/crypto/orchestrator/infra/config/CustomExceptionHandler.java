package com.davalores.crypto.orchestrator.infra.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.domain.model.exception.TicketRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "com.davalores.crypto.orchestrator")
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	//RFC 9457 - Problem Details for HTTP APIs
	@ExceptionHandler({ TicketRuntimeException.class })
	public ResponseEntity<Object> handleWsRenaperLoginException(TicketRuntimeException ex, WebRequest request) {
		log.error("TicketRuntimeException - INIT");		
		log.error("TicketRuntimeException - " + ex.toString());	
		
		HttpStatus status = HttpStatus.PRECONDITION_FAILED;
		if ( ex instanceof LoginException ) {
			status = HttpStatus.UNAUTHORIZED;
		}

		String detalle;
		if (ex.getDescripcion() != null && !ex.getDescripcion().isEmpty()) {
			detalle = ex.getDescripcion();
		} else {			
			detalle = ex.toString();
		}
		
		ProblemDetail problemDetail
        	= ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detalle);
		problemDetail.setProperty("ticket", ex.getTicketError());
		problemDetail.setProperty("fecha", ex.getDate());
		problemDetail.setProperty("tipo", ex.getErrorType());
		problemDetail.setProperty("codigo", ex.getCodigo());
		
		log.error("TicketRuntimeException - FIN");
		return ResponseEntity.status(status).body(problemDetail);						
	}
	
}
