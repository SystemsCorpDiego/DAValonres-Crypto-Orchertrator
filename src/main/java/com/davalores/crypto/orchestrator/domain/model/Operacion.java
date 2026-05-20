package com.davalores.crypto.orchestrator.domain.model;

public class Operacion {
	String id; 				// UUID de cryptoOrchestrator (DA Valores)
	
	String idProvider; 		// UUID4 - Ident del proveedor (Ripio)
	
	String estado; 			// "RECHA_BUY_DAV"  => estado DA Valores - OpBuyApproval Ripsa Rechaza (operacion=BUY)
							// "APRO_BUY_DAV"   => estado DA Valores - OpBuyApproval Ripsa Aprobada (operacion=BUY)
							// "APRO_OP_RIPIO"  => estado Ripio (RipioWebHookMessageOpResultDto.succeed=true)
							// "RECHA_OP_RIPIO" => estado Ripio (RipioWebHookMessageOpResultDto.succeed=false)
							// "RESER_OK_DAV"   => estado DA Valores - Se hizo la reserva de los fondos  (operacion=BUY)
							// "DEPO_OK_DAV"    => estado DA Valores - Se hizo el depñosito de los fondos  (operacion=SELL)
	
	String operacion;       // "BUY" o "SELL"   => informado por Ripio (RipioWebHookMessageOpResultDto.op_type)
	String idClient;        // PK del cliente en DA Valores 
	
	
	//TODO: Cuando se analice VisualBolsa vemos que datos registramos de las transacciones 
	// RESERVA/EXTRACCION y DEPOSITO.-
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}	
	
}
