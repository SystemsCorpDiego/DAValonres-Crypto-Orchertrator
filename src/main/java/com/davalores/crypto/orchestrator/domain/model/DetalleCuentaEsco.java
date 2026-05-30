package com.davalores.crypto.orchestrator.domain.model;

public class DetalleCuentaEsco {
	private Long comitente;
	private String comitenteDescripcion;		
	private String cuit;
	
	
	
	public Long getComitente() {
		return comitente;
	}
	public void setComitente(Long comitente) {
		this.comitente = comitente;
	}
	public String getComitenteDescripcion() {
		return comitenteDescripcion;
	}
	public void setComitenteDescripcion(String comitenteDescripcion) {
		this.comitenteDescripcion = comitenteDescripcion;
	}
	public String getCuit() {
		return cuit;
	}
	public void setCuit(String cuit) {
		this.cuit = cuit;
	}
	
	
	@Override
	public String toString() {
		return "DetalleCuentaEsco [comitente=" + comitente + ", comitenteDescripcion=" + comitenteDescripcion
				+ ", cuit=" + cuit + "]";
	}

	
	
}
