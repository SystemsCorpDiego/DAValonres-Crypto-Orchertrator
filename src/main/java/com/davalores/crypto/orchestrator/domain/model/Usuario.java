package com.davalores.crypto.orchestrator.domain.model;

import java.util.HashSet;
import java.util.Set;

 
public class Usuario {
	
	private Integer id;
	
	private String usuario;
	private String clave;
	private String descripcion;
	
	private Boolean habilitado;
	private Boolean dfa;
	private String dfaSemilla;
	
	private Set<Perfil> perfiles = new HashSet<>(); 
	
	private String escoId;
	private String claveEsco;	
	private String ripioId; 
	private Boolean ripioHabilitado;
	
	 
	
	public Usuario() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean getHabilitado() {
		return habilitado;
	}
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	public Boolean getDfa() {
		return dfa;
	}
	public void setDfa(Boolean dfa) {
		this.dfa = dfa;
	}
	public String getDfaSemilla() {
		return dfaSemilla;
	}
	public void setDfaSemilla(String dfaSemilla) {
		this.dfaSemilla = dfaSemilla;
	}
	public Set<Perfil> getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(Set<Perfil> perfiles) {
		this.perfiles = perfiles;
	}
	public String getEscoId() {
		return escoId;
	}
	public void setEscoId(String escoId) {
		this.escoId = escoId;
	}
	public String getRipioId() {
		return ripioId;
	}
	public void setRipioId(String ripioId) {
		this.ripioId = ripioId;
	}
	public Boolean getRipioHabilitado() {
		return ripioHabilitado;
	}
	public void setRipioHabilitado(Boolean ripioHabilitado) {
		this.ripioHabilitado = ripioHabilitado;
	}	
	public String getClaveEsco() {
		return claveEsco;
	}
	public void setClaveEsco(String claveEsco) {
		this.claveEsco = claveEsco;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", clave=" + clave + ", descripcion=" + descripcion
				+ ", habilitado=" + habilitado + ", dfa=" + dfa + ", dfaSemilla=" + dfaSemilla + ", perfiles="
				+ perfiles + ", escoId=" + escoId + ", claveEsco=" + claveEsco + ", ripioId=" + ripioId
				+ ", ripioHabilitado=" + ripioHabilitado + "]";
	}

}
