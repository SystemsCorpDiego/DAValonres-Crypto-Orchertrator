package com.davalores.crypto.orchestrator.app.service.trade;

import java.util.List;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.trade.ConsultarMonedaCryptoPortIn;
import com.davalores.crypto.orchestrator.domain.model.MonedaCrypto;
import com.davalores.crypto.orchestrator.infra.adapter.out.ConsultarMonedaCryptoAdapterOut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsultarMonedaCryptoService implements ConsultarMonedaCryptoPortIn {

	private final ConsultarMonedaCryptoAdapterOut adapterOut;
	
	public ConsultarMonedaCryptoService (ConsultarMonedaCryptoAdapterOut adapterOut) {
		this.adapterOut = adapterOut;
	}
	
	@Override
	public List<MonedaCrypto> run() {
		// TODO Auto-generated method stub
		List<MonedaCrypto> lst = adapterOut.run();
		
		return lst;
	}
	
}
