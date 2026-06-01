package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.infra.adapter.out.dto.OperationResponseDto;

@Mapper(componentModel = "spring")
public interface OperationResponseDtoMapper {

	@Mapping( target = "idExterno", source = "external_ref" ) 
    @Mapping( target = "idExternoProveedor", source = "id" )
    @Mapping( target = "quoteId", source = "quote_id" )
    @Mapping( target = "trxIdExternoProveedor", source = "txn_id" )
    @Mapping( target = "idExternoCliente", source = "end_user_id" )
    @Mapping( target = "ratio", source = "rate" )
    @Mapping( target = "ratioMercado", source = "market_rate" )
    @Mapping( target = "comision", source = "charged_fee" )
    @Mapping( target = "comisionCrypto", source = "crypto_charged_fee" )
    @Mapping( target = "activoCotiCantidad", source = "quote_amount" )
    @Mapping( target = "activoBaseCantidad", source = "base_amount" )
    @Mapping( target = "activoBase", source = "base_asset" )
    @Mapping( target = "activoCoti", source = "quote_asset" )
    @Mapping( target = "creadoEnProveedor", source = "created_at" )
    @Mapping( target = "proveedor", constant = "RIPIO" )
    public Operacion run(OperationResponseDto dto);
	
}
