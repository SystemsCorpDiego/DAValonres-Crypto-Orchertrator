package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.SaldoEscoDto;

@Mapper(componentModel = "spring")
public interface SaldoCuentaMapper {

	public SaldoEscoDto run(SaldoCuentaEsco dto);
	
}
