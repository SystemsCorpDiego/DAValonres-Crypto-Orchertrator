package com.davalores.crypto.orchestrator.domain.model;

public class DetalleCuentaEsco {
	private String comitente;
	private String comitenteDescripcion;		
	private String cuit;
	
	
	public String getComitente() {
		return comitente;
	}
	public void setComitente(String comitente) {
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
