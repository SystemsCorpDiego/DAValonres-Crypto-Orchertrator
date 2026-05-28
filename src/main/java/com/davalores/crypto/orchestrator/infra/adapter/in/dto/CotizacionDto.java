package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class CotizacionDto {
	private String activoBase;
	private String activoCoti;
	private BigDecimal compraComision;
	private BigDecimal compraRatio;
	private ZonedDateTime expira;
	private String idExterno;
	private String idExternoProveedor;
	private BigDecimal ventaComision;
	private BigDecimal ventaRatio;
}
