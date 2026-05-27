package com.davalores.crypto.orchestrator.app.port.in.trade;

import java.util.List;

import com.davalores.crypto.orchestrator.domain.model.MonedaCrypto;

public interface ConsultarMonedaCryptoPortIn {

	public List<MonedaCrypto> run();
	
}
