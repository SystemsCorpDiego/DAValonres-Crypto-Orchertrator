package com.davalores.crypto.orchestrator.domain.model.exception;

public class ClienteRipioException extends TicketRuntimeException {

	private static final long serialVersionUID = 4376055279152056078L;

	public ClienteRipioException(String message) {
		super(message);
	}

	public ClienteRipioException(String codigo, String message) {
		super(codigo, message);
	}

	public ClienteRipioException(String status, String codigo, String descripcion) {
		super(status, codigo, descripcion);		
	}
	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.CLIENTE_ERROR.name();
	}


}
