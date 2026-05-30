package com.davalores.crypto.orchestrator.infra.adapter.out.dto;

import lombok.Data;

@Data
public class ReciboCobroEscoDto extends ComprobateEscoDto {

	 private Integer codCtaLibradora;
	 private Boolean esTransferenciaBrokerExterior;
	 private String ctaBancariaDepositoEcheq;

}
