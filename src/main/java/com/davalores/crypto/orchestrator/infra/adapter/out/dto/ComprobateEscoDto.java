package com.davalores.crypto.orchestrator.infra.adapter.out.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ComprobateEscoDto {
	  private Long cuenta; // : 0,
	  private String moneda;
	  private String fechaConcertacion; //": "2026-05-30T10:22:31.263Z",
	  private String fechaLiquidacion; //": "2026-05-30T10:22:31.263Z",
	  private BigDecimal importe; 
	  private Integer tpCambioMovPais; //": 0,
	  private Long cuentaContable; //": 0,
	  private Long cuentaBancariaComitente; //": 0,
	  private String numReferencia; //": "string",
	  private String comentario; 
	  private String idOrigen; 
	  private Boolean esEcheq; 
	  private Long numeroCheque; 
	  private Boolean cobrarGastosGestionBancaria; 
	  private Integer tpOperacion;
	  private Integer numOperReferencia; 
	  private Boolean esGC2; //": false,
	  private String tipoTransfMep;
}
