package com.davalores.crypto.orchestrator.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class Usuario {
	
	private String id;
	private String descripcion;
	
	private Boolean habilitado;
	private Boolean dfa;
	private List<EPerfil> perfiles;
	
	private String visualBolsaId; 
	private String ripioId; 
	private Boolean ripioHabilitado; 
	
}
