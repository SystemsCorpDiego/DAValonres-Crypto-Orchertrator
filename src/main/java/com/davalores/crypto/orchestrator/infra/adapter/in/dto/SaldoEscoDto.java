package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SaldoEscoDto {

	private String comitente;
	private BigDecimal cantidad;
	
}
