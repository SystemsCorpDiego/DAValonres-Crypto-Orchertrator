package com.davalores.crypto.orchestrator.app.port.out.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CrearComprobantePagoEscoDto {

	private BigDecimal importe;
	private String moneda;	
	private Long cuenta;					//Ej.: 300314
	private Long codCtaBancariaComitente;   //Ej.: 1962
	
}
