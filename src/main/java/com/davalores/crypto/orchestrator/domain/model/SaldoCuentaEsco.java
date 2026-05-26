package com.davalores.crypto.orchestrator.domain.model;

import java.math.BigDecimal;

public class SaldoCuentaEsco {

	private String comitente;
	private BigDecimal cantidad;
	
	
	public String getComitente() {
		return comitente;
	}
	public void setComitente(String comitente) {
		this.comitente = comitente;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return "SaldoCuentaEsco [comitente=" + comitente + ", cantidad=" + cantidad + "]";
	}
	
}
