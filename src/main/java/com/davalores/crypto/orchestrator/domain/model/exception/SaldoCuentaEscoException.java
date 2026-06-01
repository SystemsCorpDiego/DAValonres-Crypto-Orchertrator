package com.davalores.crypto.orchestrator.domain.model.exception;

public class SaldoCuentaEscoException extends TicketRuntimeException {

	private static final long serialVersionUID = 8302035146099195574L;

	public SaldoCuentaEscoException(String codigo, String descripcion) {
		super(codigo, descripcion);
	}
	
	public SaldoCuentaEscoException(String codigo, String descripcion, Exception e ) {
		super(codigo, descripcion, e);
	}
	
	public SaldoCuentaEscoException(String status, String codigo, String descripcion) {
		super(status, codigo, descripcion);		
	}
	
	@Override
	public String getErrorType() {
		// TODO Auto-generated method stub
		return ErrorTypeEnum.CUENTA_SALDO_ERROR.getType();
	}

}
