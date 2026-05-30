package com.davalores.crypto.orchestrator.infra.adapter.out.dto;

public class DetalleCuentaEscoDetalleDto {
	private Long numComitente;
	private String descComitente;
	private String cuit;
	
	
	public Long getNumComitente() {
		return numComitente;
	}
	public void setNumComitente(Long numComitente) {
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
