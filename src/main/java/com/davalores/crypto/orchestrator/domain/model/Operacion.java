package com.davalores.crypto.orchestrator.domain.model;

import java.math.BigDecimal;

public class Operacion {
	String id; 				// UUID de cryptoOrchestrator (DA Valores)
	String idExterno;
	String idExternoProveedor; 		// UUID4 - Ident del proveedor (Ripio)
	String quoteId;
	String trxIdExternoProveedor;
	String idExternoCliente;
	BigDecimal ratio;
	BigDecimal ratioMercado;
	BigDecimal comision;
	BigDecimal comisionCrypto;
	String activoBase;
	String activoCoti;
	BigDecimal activoCotiCantidad;
	BigDecimal activoBaseCantidad;
	String proveedor; 		// "RIPIO"
	
	
	String tipo;       		// "BUY" o "SELL"   => informado por Ripio (RipioWebHookMessageOpResultDto.op_type)
	        				// PK del cliente en DA Valores 


	//TODO: Cuando se analice VisualBolsa vemos que datos registramos de las transacciones 
	// RESERVA/EXTRACCION y DEPOSITO.-
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdExterno() {
		return idExterno;
	}


	public void setIdExterno(String idExterno) {
		this.idExterno = idExterno;
	}


	public String getIdExternoProveedor() {
		return idExternoProveedor;
	}


	public void setIdExternoProveedor(String idExternoProveedor) {
		this.idExternoProveedor = idExternoProveedor;
	}


	public String getQuoteId() {
		return quoteId;
	}


	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}


	public String getTrxIdExternoProveedor() {
		return trxIdExternoProveedor;
	}


	public void setTrxIdExternoProveedor(String trxIdExternoProveedor) {
		this.trxIdExternoProveedor = trxIdExternoProveedor;
	}


	public String getIdExternoCliente() {
		return idExternoCliente;
	}


	public void setIdExternoCliente(String idExternoCliente) {
		this.idExternoCliente = idExternoCliente;
	}


	public BigDecimal getRatio() {
		return ratio;
	}


	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}


	public BigDecimal getRatioMercado() {
		return ratioMercado;
	}


	public void setRatioMercado(BigDecimal ratioMercado) {
		this.ratioMercado = ratioMercado;
	}


	public BigDecimal getComision() {
		return comision;
	}


	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}


	public BigDecimal getComisionCrypto() {
		return comisionCrypto;
	}


	public void setComisionCrypto(BigDecimal comisionCrypto) {
		this.comisionCrypto = comisionCrypto;
	}


	public String getActivoBase() {
		return activoBase;
	}


	public void setActivoBase(String activoBase) {
		this.activoBase = activoBase;
	}


	public String getActivoCoti() {
		return activoCoti;
	}


	public void setActivoCoti(String activoCoti) {
		this.activoCoti = activoCoti;
	}


	public BigDecimal getActivoCotiCantidad() {
		return activoCotiCantidad;
	}


	public void setActivoCotiCantidad(BigDecimal activoCotiCantidad) {
		this.activoCotiCantidad = activoCotiCantidad;
	}


	public BigDecimal getActivoBaseCantidad() {
		return activoBaseCantidad;
	}


	public void setActivoBaseCantidad(BigDecimal activoBaseCantidad) {
		this.activoBaseCantidad = activoBaseCantidad;
	}


	public String getProveedor() {
		return proveedor;
	}


	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
