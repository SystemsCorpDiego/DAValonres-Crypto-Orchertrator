package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OperacionDto {
	String tipo;
    String activoBase;
	String activoCoti;
	BigDecimal ratio;
	BigDecimal activoCotiCantidad;
	BigDecimal activoBaseCantidad;
}
