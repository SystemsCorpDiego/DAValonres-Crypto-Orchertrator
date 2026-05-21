package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.davalores.crypto.orchestrator.app.port.out.OperacionRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.OperacionEntity;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.OperacionEntityMapper;

@Repository
public class OperacionJpaRepositoryAdapterOut implements OperacionRepositoryPortOut {

	private final OperacionJpaRepository repository;
	private final OperacionEntityMapper mapper;
	
	public OperacionJpaRepositoryAdapterOut(OperacionJpaRepository repository, OperacionEntityMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	public Operacion save(Operacion operacion) {		
		
		OperacionEntity reg = mapper.run(operacion);
		reg.setCreadoEn(LocalDateTime.now());
		repository.save(reg);
		operacion = mapper.run(reg);
		
		return operacion;
	}
	
}
