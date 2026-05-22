package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.OperacionEntity;

@Mapper(componentModel = "spring")
public interface OperacionEntityMapper {

	@Mapping( target = "trxIdProveedor", source = "trxIdExternoProveedor" )
	public OperacionEntity run(Operacion operacion);
	
	public Operacion run(OperacionEntity operacion);
	
}
