package com.davalores.crypto.orchestrator.domain.model.exception;

import org.springframework.http.ProblemDetail;

public class CustomProblemDetail extends ProblemDetail {

	private static final long serialVersionUID = 5626466674385338019L;
	
	private String ticket;
	private String fecha;
	private String tipo;
	private String codigo;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "CustomProblemDetail [ticket=" + ticket + ", fecha=" + fecha + ", tipo=" + tipo + ", codigo=" + codigo
				+ ", getType()=" + getType() + ", getTitle()=" + getTitle() + ", getStatus()=" + getStatus()
				+ ", getDetail()=" + getDetail() + ", getInstance()=" + getInstance() + ", getProperties()="
				+ getProperties() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ ", initToStringContent()=" + initToStringContent() + ", getClass()=" + getClass() + "]";
	}
	 

	
	
}
