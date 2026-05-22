package com.davalores.crypto.orchestrator.domain.model.exception;

public class CotizacionException extends TicketRuntimeException {

	private static final long serialVersionUID = 7532339531371725419L;

	public CotizacionException(String message) {
		super(message);
	}

	public CotizacionException(String codigo, String message) {
		super(codigo, message);
	}

	@Override
	public String getErrorType() {
		return ErrorTypeEnum.QUOTE_ERROR.name();
	}

}
