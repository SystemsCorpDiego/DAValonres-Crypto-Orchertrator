package com.davalores.crypto.orchestrator.domain.model.exception;

public class BusinessException extends TicketRuntimeException {
 
	private static final long serialVersionUID = -5631117566845447539L;

	public BusinessException(String message) {
		super(message);
	}

	@Override
	public String getErrorType() {
		return ErrorTypeEnum.BUSINESS_ERROR.name();
	}

}
