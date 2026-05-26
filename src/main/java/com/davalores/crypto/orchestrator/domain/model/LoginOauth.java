package com.davalores.crypto.orchestrator.domain.model;

import lombok.Data;

@Data
public class LoginOauth extends TokenOauth {

	private DetalleCuentaEsco cuentaEsco;
	
}
