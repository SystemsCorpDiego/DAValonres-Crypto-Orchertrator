package com.davalores.crypto.orchestrator.domain.model.exception;

public class LoginDfaException extends TicketRuntimeException {

	private static final long serialVersionUID = -7646663802752197491L;

	public LoginDfaException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }

	@Override
	public String getErrorType() {
		return ErrorTypeEnum.LOGIN_DFA_ERROR.getType();
	}

}
