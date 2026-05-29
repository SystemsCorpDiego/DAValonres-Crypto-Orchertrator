package com.davalores.crypto.orchestrator.infra.adapter.out.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "operaciones")
@Data
public class OperacionEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id; 				
	String idExterno;				// UUID de cryptoOrchestrator (DA Valores)
	String idExternoProveedor; 		// UUID4 - Ident del proveedor (Ripio)
	String idExternoCotizacion;	
	String quoteId;
	
	@Column(name = "trx_id_proveedor")
	String trxIdProveedor;	
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
	String tipo;
	Integer usuarioId; 		//FK a usuario (DA Valores)
	String creadoEnProveedor;
	LocalDateTime creadoEn;

	
}
