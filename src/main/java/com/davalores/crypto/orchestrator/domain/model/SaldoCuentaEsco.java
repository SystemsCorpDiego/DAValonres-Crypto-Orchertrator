package com.davalores.crypto.orchestrator.domain.model;

import java.math.BigDecimal;

public class SaldoCuentaEsco {

	private Long comitente;
	private BigDecimal cantidad;
	
	
	public Long getComitente() {
		return comitente;
	}
	public void setComitente(Long comitente) {
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
