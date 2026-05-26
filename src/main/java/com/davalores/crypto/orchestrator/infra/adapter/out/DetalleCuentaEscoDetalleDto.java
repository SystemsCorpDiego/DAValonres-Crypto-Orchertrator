package com.davalores.crypto.orchestrator.infra.adapter.out;

public class DetalleCuentaEscoDetalleDto {
	private String numComitente;
	private String descComitente;
	private String cuit;
	
	public String getNumComitente() {
		return numComitente;
	}
	public void setNumComitente(String numComitente) {
		this.numComitente = numComitente;
	}
	public String getDescComitente() {
		return descComitente;
	}
	public void setDescComitente(String descComitente) {
		this.descComitente = descComitente;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	
}
