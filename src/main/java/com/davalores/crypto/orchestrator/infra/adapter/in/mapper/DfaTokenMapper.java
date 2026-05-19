package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.orchestrator.domain.model.DfaToken;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DfaTokenDto;

@Mapper(componentModel = "spring")
public interface DfaTokenMapper {

	@Mapping(target = "semilla", source = "sharedSecret")	
	@Mapping(target = "semillaCodigoBarra", source = "authenticatorBarCode")	
	@Mapping(target = "semillaQr", source = "qrCodeImg")
	public DfaTokenDto run(DfaToken token);
	
}
