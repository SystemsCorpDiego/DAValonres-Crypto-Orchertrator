package com.davalores.crypto.orchestrator.app.service.trade;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearOperacionPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CrearComprobantePagoEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.CrearOperacionRipioPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetCotizacionRipioPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetDetalleCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetSaldoCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.OperacionRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.port.out.dto.CrearComprobantePagoEscoDto;
import com.davalores.crypto.orchestrator.domain.model.Cotizacion;
import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearOperacionService implements CrearOperacionPortIn {

	private final OperacionRepositoryPortOut operacionRepository;
	private final CrearOperacionRipioPortOut crearOperacionRipioPortOut;
	private final CrearComprobantePagoEscoPortOut crearComprobantePagoEscoPortOut;
	private final GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut;
	private final GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut;
	private final GetCotizacionRipioPortOut getCotizacionRipioPortOut;
	private final Boolean validarSaldo;
	
	public CrearOperacionService( 
			CrearOperacionRipioPortOut crearOperacionRipioPortOut, 
			OperacionRepositoryPortOut operacionRepository,
			CrearComprobantePagoEscoPortOut crearComprobantePagoEscoPortOut,
			GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut,
			GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut,
			GetCotizacionRipioPortOut getCotizacionRipioPortOut,
			@Value("#{new Boolean('${validar-saldo}')}") Boolean validarSaldo) {
		this.operacionRepository = operacionRepository;
		this.crearOperacionRipioPortOut = crearOperacionRipioPortOut;
		this.crearComprobantePagoEscoPortOut = crearComprobantePagoEscoPortOut;
		this.getDetalleCuentaEscoPortOut = getDetalleCuentaEscoPortOut;		
		this.getSaldoCuentaEscoPortOut = getSaldoCuentaEscoPortOut;
		this.getCotizacionRipioPortOut = getCotizacionRipioPortOut;		
		this.validarSaldo = validarSaldo;
		/*
		if (validarSaldo.equals("true")) {
			this.validarSaldo = true;
		} else {
			this.validarSaldo = false;
		}
		*/
	}
	
	
	@Override
	public Operacion run(Usuario usuarioLogin, CrearOperacion dto) {
		// TODO sacar usuarioId del token, recuperar ripio_id del usuario,  y luego llamar a Ripio para crear la operacion
		log.debug("input -> usuarioLogin: {} - dto: {}", usuarioLogin, dto);
		
		if ( usuarioLogin.getRipioId() == null )
			throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "El usuario no tiene asociado un RipioId");
		dto.setRipioId(usuarioLogin.getRipioId());
		dto.setUsuarioId(usuarioLogin.getId());

		//1) Recupero Cuenta y Saldo
		DetalleCuentaEsco detalleCuentaEsco = getDetalleCuentaEscoPortOut.run(usuarioLogin);
		SaldoCuentaEsco saldoCuentaEsco = getSaldoCuentaEscoPortOut.run(usuarioLogin, detalleCuentaEsco.getComitente());
		//2) Recupero Cotizacion Ripio
		Cotizacion cotizacion = getCotizacionRipioPortOut.run(dto.getCotizacionId());
		
		//3) Validar Saldo para la Compra
		if ( validarSaldo && dto.getTipo().equals("COMPRA") ) {
			if ( !cotizacion.getActivoCoti().equals("ARS") ) {
				throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "La moneda de la cotización debe ser ARS para realizar una operación de COMPRA. Moneda de la cotización: " + cotizacion.getActivoCoti());
			}
			BigDecimal importe = dto.getCantidad().multiply(cotizacion.getCompraRatio());
			if (saldoCuentaEsco.getCantidad().compareTo(importe) < 0) {
				throw new BusinessException(ErrorCodeEnum.BUSINESS_ERROR.toString(),
						"El saldo disponible en ESCO es insuficiente para realizar la operación. Saldo disponible: "
								+ saldoCuentaEsco.getCantidad() + " - Importe requerido: " + importe);
			}
		}
		
		//4) Crear Operacion en Ripio
		Operacion operacion = crearOperacionRipioPortOut.run(dto);

		//5) Guardo la Operacion en MiddleWare
		operacion = operacionRepository.save(operacion);
		
		
		
		//6) Genero el Comprobante de Pago en ESCO
		CrearComprobantePagoEscoDto comprobante = new CrearComprobantePagoEscoDto();
		BigDecimal importe = operacion.getActivoCotiCantidad().multiply(operacion.getRatio());
		comprobante.setImporte(importe);
		comprobante.setMoneda(operacion.getActivoCoti());
		
		comprobante.setCuenta(detalleCuentaEsco.getComitente());
		comprobante.setCodCtaBancariaComitente(null); //TODO: ver esto !!!
		
		Long comprobanteNro = crearComprobantePagoEscoPortOut.run(comprobante);
		
		//5) Guardo el Comprobante ESCO en la Operacion
		operacion.setIdComprobanteEsco(comprobanteNro);
		operacion = operacionRepository.save(operacion);
		
		log.debug("output -> {}", operacion);
		return operacion;
	}
	
}
