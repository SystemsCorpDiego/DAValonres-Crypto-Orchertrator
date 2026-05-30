package com.davalores.crypto.orchestrator.infra.adapter.out.dto;

import lombok.Data;

@Data
public class ComprobantePagoEscoDto extends ComprobateEscoDto {

	private Boolean controlaSaldoMonetario;
	
}
