package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import lombok.Data;

@Data
public class UsuarioDto {

	String id;
	String usuario;
	String descripcion;	
	String escoId;
	Boolean habilitado;
	Boolean dfa;
	String ripioId;
	Boolean ripioHabilitado;
	
}
