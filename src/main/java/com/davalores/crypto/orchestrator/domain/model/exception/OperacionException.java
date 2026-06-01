package com.davalores.crypto.orchestrator.domain.model.exception;

public class OperacionException extends TicketRuntimeException {

	private static final long serialVersionUID = -5082904457429296361L;

	public OperacionException(String message) {
		super(message);
	}

	public OperacionException(String codigo, String message) {
		super(codigo, message);
	}

	public OperacionException(String status, String codigo, String descripcion) {
		super(status, codigo, descripcion);		
	}
	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.OPERATION_ERROR.name();
	}
	
}
