package com.davalores.crypto.orchestrator.domain.model.exception;

public class LoginException extends TicketRuntimeException {

	private static final long serialVersionUID = 3269212242294963725L;

	public LoginException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }

	@Override
	public String getErrorType() {
		return ErrorTypeEnum.LOGIN_ERROR.getType();
	}
	

}
