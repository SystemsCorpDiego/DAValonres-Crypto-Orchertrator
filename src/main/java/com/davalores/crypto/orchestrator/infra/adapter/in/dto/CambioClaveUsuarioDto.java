package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import lombok.Data;

@Data
public class CambioClaveUsuarioDto {
	private String clave;
	private String claveNueva;
}
