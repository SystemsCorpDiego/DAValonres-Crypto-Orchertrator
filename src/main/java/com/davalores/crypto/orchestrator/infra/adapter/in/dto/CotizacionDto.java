package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CotizacionDto {
	private String activoBase;
	private String activoCoti;
	private BigDecimal compraComision;
	private BigDecimal compraRatio;
	private LocalDateTime expira;
	private String idExterno;
	private String idExternoProveedor;
	private BigDecimal ventaComision;
	private BigDecimal ventaRatio;
}
