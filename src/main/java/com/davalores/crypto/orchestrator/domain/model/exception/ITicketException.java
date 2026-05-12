package com.davalores.crypto.orchestrator.domain.model.exception;

public interface ITicketException {
	public String getCodigo();
	public String errorToString();
	public String getTicketError();
}
