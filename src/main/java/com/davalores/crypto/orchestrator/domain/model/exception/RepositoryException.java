package com.davalores.crypto.orchestrator.domain.model.exception;

public class RepositoryException  extends TicketRuntimeException {

	private static final long serialVersionUID = 1L;

	public RepositoryException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	public RepositoryException(String codigo, String descripcion, Exception e ) {
		super(codigo, descripcion, e);
	}
	
	
	@Override
	public String getErrorType() {
		return ErrorTypeEnum.REPOSITORY_ERROR.getType();
	}
	
}
