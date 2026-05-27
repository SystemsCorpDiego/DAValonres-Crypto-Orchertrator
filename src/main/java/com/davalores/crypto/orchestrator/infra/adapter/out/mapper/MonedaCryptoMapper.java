package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.MonedaCrypto;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.MonedaCryptoEntity;

@Mapper(componentModel = "spring")
public interface MonedaCryptoMapper {

	public List<MonedaCrypto> run(List<MonedaCryptoEntity> dto );
	public MonedaCrypto run(MonedaCryptoEntity dto );
	
}
