package com.davalores.crypto.orchestrator.app.port.out;

import java.util.List;

import com.davalores.crypto.orchestrator.domain.model.MonedaCrypto;

public interface ConsultarMonedaCryptoPortOut {
	public List<MonedaCrypto> run();
}
