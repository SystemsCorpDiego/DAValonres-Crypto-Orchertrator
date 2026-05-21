package com.davalores.crypto.orchestrator.domain.model;

import java.math.BigDecimal;

public class CrearOperacion {

	private String tipo;

	private Integer usuarioId;
	private String ripioId;
	
	private String cotizacionId;
	private BigDecimal cantidad;
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public String getRipioId() {
		return ripioId;
	}
	public void setRipioId(String ripioId) {
		this.ripioId = ripioId;
	}


	public String getCotizacionId() {
		return cotizacionId;
	}
	public void setCotizacionId(String cotizacionId) {
		this.cotizacionId = cotizacionId;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
