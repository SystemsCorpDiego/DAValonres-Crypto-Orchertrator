package com.davalores.crypto.orchestrator.domain.model.exception;

import java.time.LocalDateTime;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class TicketRuntimeException extends RuntimeException  implements ITicketException {

	private static final long serialVersionUID = 4407349629165956040L;
	private String codeWebsite = "DAVMW";
	public String codigo;
	public String descripcion;
	private String statusString;

	private Integer nroTicket;
	private LocalDateTime date;


	public TicketRuntimeException(Exception e) {
        super(e);
        
        if ( e instanceof TicketRuntimeException  ) {
        	this.codeWebsite = ((TicketRuntimeException) e).getCodeWebsite();
        	this.nroTicket = ((TicketRuntimeException) e).getNroTicket();
        	this.date = ((TicketRuntimeException) e).getDate();
        	this.codigo = ((TicketRuntimeException) e).getCodigo();
        	this.descripcion = ((TicketRuntimeException) e).getDescripcion();
        } else {
        	generarTicket();
	    }
    }
	
	public TicketRuntimeException(String codigo, String descripcion, Exception e) {
		super(e);
		
		if ( e instanceof TicketRuntimeException  ) {
        	this.nroTicket = ((TicketRuntimeException) e).getNroTicket();
        	this.codeWebsite = ((TicketRuntimeException) e).getCodeWebsite();
        	this.date = ((TicketRuntimeException) e).getDate();
          	this.codigo = ((TicketRuntimeException) e).getCodigo();
        	this.descripcion  = ((TicketRuntimeException) e).getDescripcion();
        } else {
        	generarTicket();
	    }
		this.codigo = codigo;
		if ( descripcion != null)
			this.descripcion = ((this.descripcion==null) ? "" : this.descripcion+" - ") + descripcion;
	}
	
	public TicketRuntimeException(String codigo, Exception e) {
		super(e);
		
		if ( e instanceof TicketRuntimeException  ) {
        	this.nroTicket = ((TicketRuntimeException) e).getNroTicket();
        	this.codeWebsite = ((TicketRuntimeException) e).getCodeWebsite();
        	this.date = ((TicketRuntimeException) e).getDate();
          	this.codigo = ((TicketRuntimeException) e).getCodigo();
        	this.descripcion = ((TicketRuntimeException) e).getDescripcion();
         } else {
        	generarTicket();
	    }
		this.codigo = codigo;
	}
	
	public TicketRuntimeException(String codigo, String descripcion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		
        generarTicket();
	}
	
	public TicketRuntimeException(String statusString, String codigo, String descripcion) {
		super();
		this.statusString = statusString;
		this.codigo = codigo;
		this.descripcion = descripcion;
		
        generarTicket();
	}
	
	public TicketRuntimeException(String message) {
        super(message);
        this.descripcion = message;
        
        generarTicket();
    }

	
	private void generarTicket() {
		Random randomNum = new Random();
        this.nroTicket = randomNum.nextInt(10000000);        
        this.date = LocalDateTime.now();
	}

	public String getTicketError() {		
		if ( this.getCodeWebsite() != null && this.getNroTicket() != null ) {
			return this.getCodeWebsite() + "-" + String.format("%08d", this.getNroTicket())  ;
		} else {
			if ( this.getCodeWebsite() != null ) {
				return this.getCodeWebsite() + "-00000000";
			} else {
				return "WEB-00000000";
			}				
		}
	}

	public String errorToString() {
		String msg = getTicketError();
		
    	if ( this.getCodigo() != null ) {
    		msg += " - (code: " + this.getCodigo() + ") ";
    	}
    	if ( this.getDescripcion() != null ) {
    		msg += " - " + this.getDescripcion();
    	}    	

    	if  ( this.getCause() != null ) {
			msg += "- Cause: " + this.getCause();
		}
		if  ( this.getMessage() != null ) {
			msg += "- Message: " + this.getMessage();
		}
		if  ( this.getStackTrace() != null ) {
			msg += "- StackTrace: " + this.getStackTrace();
		}
		msg += "- e.toString(): " + this.toString();
	
		return msg;
	}

	@Override
	public String toString() {
		return "TicketRuntimeException [codeWebsite=" + codeWebsite + ", codigo=" + codigo + ", descripcion="
				+ descripcion + ", statusString=" + statusString + ", nroTicket=" + nroTicket + ", date=" + date + "]";
	}


	
}