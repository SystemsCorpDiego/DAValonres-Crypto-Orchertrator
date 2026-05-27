package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.List;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.ConsultarMonedaCryptoPortOut;
import com.davalores.crypto.orchestrator.domain.model.MonedaCrypto;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.MonedaCryptoEntity;
import com.davalores.crypto.orchestrator.infra.adapter.out.mapper.MonedaCryptoMapper;
import com.davalores.crypto.orchestrator.infra.adapter.out.repository.MonedaCryptoJpaRepository;

@Service
public class ConsultarMonedaCryptoAdapterOut implements ConsultarMonedaCryptoPortOut {

	private final MonedaCryptoJpaRepository repository;
	private final MonedaCryptoMapper mapper;
	
	 
	public ConsultarMonedaCryptoAdapterOut(MonedaCryptoJpaRepository repository,
			MonedaCryptoMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public List<MonedaCrypto> run() {
	 
		List<MonedaCryptoEntity> lst = repository.findAll();
		List<MonedaCrypto> cons = mapper.run(lst);
		
		return cons;
	}

}
