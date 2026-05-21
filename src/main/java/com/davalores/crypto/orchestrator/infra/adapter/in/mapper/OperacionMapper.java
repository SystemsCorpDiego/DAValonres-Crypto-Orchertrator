package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearOperacionDto;

@Mapper(componentModel = "spring")
public interface OperacionMapper {
	
	@Mapping(target = "tipo", source = "tipo")	
	@Mapping(target = "cotizacionId", source = "idExternoProveedorCotizacion")
	@Mapping(target = "cantidad", source = "cantidad")
	CrearOperacion run(CrearOperacionDto dto);	 

}
