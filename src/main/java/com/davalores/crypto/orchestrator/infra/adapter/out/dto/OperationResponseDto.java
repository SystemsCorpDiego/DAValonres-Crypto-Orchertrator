package com.davalores.crypto.orchestrator.infra.adapter.out.dto;

import lombok.Data;

@Data
public class OperationResponseDto {
	//Response del quote-execute
    private String id;                    // "636ed595-714b-4785-8d36-3dcac7d2567d", //trade(comercio) identifier
    private String external_ref;          // "71736720-4d62-473b-9916-015e59986e69",
    private String quote_id;              // "02642013-9310-47c4-8e85-67cba34d60a5", //Identficador de la cotización utilizada para ejecutar la transacción
    private String txn_id;                // "39402e8a-0f0e-49dc-928f-c72cf973383e", //Transaction identifier
    private String end_user_id;           // "73aa41e6-8a97-45f8-8d20-b2bd0cc91193", //endUser.external_ref
    private String rate; 	              // "140.00000000",						 // exchange rate used for conversion.
    private String market_rate;  		  // "140.00000000",
    private String charged_fee;  		  // "0.000",								 //  trade's applied fee (tarifa/comision).
    private Boolean fee_charged_in_fiat;  // true,
    private Boolean deferred_charged_fee; // true,
    private String crypto_charged_fee;    // "0.15000000000000000000",
    private String base_amount;           // "11.13622013",							 // cantidad de Unidades crypto en la cotizacion para "base currency"
    private String quote_amount;          // "1559.07081820",						 // cantidad de Unidades fiat en la cotizacion para "Quote currency"
    private String base_asset;            // "RTEST",								 // "Activo base":     Moneda base de la Cotizacion 
    private String quote_asset;           // "ARS",									 // "Activo cotizado": Moneda de cotizacion de la Cotizacion 
    private String created_at;            // "2025-01-22T20:20:26.218009Z"			 // Fecha del trade
}    