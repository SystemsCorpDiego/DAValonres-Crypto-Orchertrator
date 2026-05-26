package com.davalores.crypto.orchestrator.domain.model.exception;

public class DetalleCuentaEscoException extends TicketRuntimeException {

	private static final long serialVersionUID = 7274512814194678966L;

	public DetalleCuentaEscoException(String message) {
		super(message);
	}

	public DetalleCuentaEscoException(String codigo, String message) {
		super(codigo, message);
	}


	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.CUENTA_ERROR.name();
	}

}
