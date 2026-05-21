package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.junit.jupiter.api.Test;

import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrearOperacionRipioAdapterOutTest {

	@Test
	public void pruebaCasteo() {
		String strResponse = "{\"activoBase\":\"RTEST\",\"activoBaseCantidad\":\"10.00000000\",\"activoCoti\":\"ARS\",\"activoCotiCantidad\":\"14963.35200000\",\"comision\":\"148.15200000\",\"comision_crypto\":\"0.10000000000000000000\",\"creadoEnProveedor\":\"2026-05-21T16:08:14.332282Z\",\"idExterno\":\"a82e0fae-f7cd-4fd8-8b5d-78620bfb98df\",\"idExternoCliente\":\"e64f1f44-c824-4be7-a21a-de9278e7a8ee\",\"idExternoProveedor\":\"273aa1c6-124f-4f0b-8268-dd04f054463e\",\"proveedor\":\"RIPIO\",\"quoteId\":\"22d58e52-2ccd-4372-a080-1de1694a2b09\",\"ratio\":\"1496.33520000\",\"ratio_mercado\":\"1481.52000000\",\"tipo\":\"COMPRA\",\"trxIdExternoProveedor\":\"27096597-3848-48fe-a6d4-5e4729c55a3b\"}";
		
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//jsonMapper.registerModule(new JavaTimeModule()); 
		//jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); 
		jsonMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		jsonMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		
		try {
			Operacion operacion = jsonMapper.readValue(strResponse, Operacion.class);
			
			System.out.println("operacion: " + operacion.toString() );
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.toString() );
		}
		
		
		
	}
}
