package com.davalores.crypto.orchestrator.infra.adapter.out.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.orchestrator.domain.model.Operacion;

@Mapper(componentModel = "spring")
public interface OperacionEntityMapper {

	@Mapping( target = "trxIdProveedor", source = "trxIdExternoProveedor" )
	public OperacionEntity run(Operacion operacion);
	
	public Operacion run(OperacionEntity operacion);
	
}
