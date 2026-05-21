package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CrearOperacionDto {
	private String tipo; // COMPRA o VENTA
	private String idExternoProveedorCotizacion;
	private BigDecimal cantidad; // cantidad a comprar o vender
}
