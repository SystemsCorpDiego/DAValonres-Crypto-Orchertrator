package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CotizacionSolicitud;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CotizacionDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearCotizacionDto;

@Mapper
public interface CotizacionMapper {

	public CotizacionDto run(Cotizacion dto);
	
	public CotizacionSolicitud run(CrearCotizacionDto dto);

}
