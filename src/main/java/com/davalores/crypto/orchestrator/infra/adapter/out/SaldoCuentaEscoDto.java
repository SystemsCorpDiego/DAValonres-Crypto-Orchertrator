package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.math.BigDecimal;
import java.util.List;

public class SaldoCuentaEscoDto {

	private String abreviatura;
	private BigDecimal cantidad;
	private String descripcion;
	private String moneda;
	
	private SaldoCuentaPosicionCaucionesEscoDto posicionCauciones;

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public SaldoCuentaPosicionCaucionesEscoDto getPosicionCauciones() {
		return posicionCauciones;
	}

	public void setPosicionCauciones(SaldoCuentaPosicionCaucionesEscoDto posicionCauciones) {
		this.posicionCauciones = posicionCauciones;
	}

	@Override
	public String toString() {
		return "SaldoCuentaEscoDto [abreviatura=" + abreviatura + ", cantidad=" + cantidad + ", descripcion="
				+ descripcion + ", moneda=" + moneda + ", posicionCauciones=" + posicionCauciones + "]";
	}

	 
	
}
