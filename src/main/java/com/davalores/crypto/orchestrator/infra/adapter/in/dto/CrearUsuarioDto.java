package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import lombok.Data;

@Data
public class CrearUsuarioDto {
	String usuario;
	String clave;
	String descripcion;
}
