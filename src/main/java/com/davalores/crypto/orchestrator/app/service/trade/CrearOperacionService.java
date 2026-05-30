package com.davalores.crypto.orchestrator.app.service.trade;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearOperacionPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CrearComprobantePagoEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.CrearOperacionRipioPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetDetalleCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetSaldoCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.OperacionRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.port.out.dto.CrearComprobantePagoEscoDto;
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
	
	public CrearOperacionService( 
			CrearOperacionRipioPortOut crearOperacionRipioPortOut, 
			OperacionRepositoryPortOut operacionRepository,
			CrearComprobantePagoEscoPortOut crearComprobantePagoEscoPortOut,
			GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut,
			GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut) {
		this.operacionRepository = operacionRepository;
		this.crearOperacionRipioPortOut = crearOperacionRipioPortOut;
		this.crearComprobantePagoEscoPortOut = crearComprobantePagoEscoPortOut;
		this.getDetalleCuentaEscoPortOut = getDetalleCuentaEscoPortOut;		
		this.getSaldoCuentaEscoPortOut = getSaldoCuentaEscoPortOut;
	}
	
	
	@Override
	public Operacion run(Usuario usuarioLogin, CrearOperacion dto) {
		// TODO sacar usuarioId del token, recuperar ripio_id del usuario,  y luego llamar a Ripio para crear la operacion
		log.debug("inputParam -> usuarioLogin: {} - dto: {}", usuarioLogin, dto);
		
		if ( usuarioLogin.getRipioId() == null )
			throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "El usuario no tiene asociado un RipioId");
		dto.setRipioId(usuarioLogin.getRipioId());
		dto.setUsuarioId(usuarioLogin.getId());

		//1) Recupero Cuenta ESCO
		DetalleCuentaEsco detalleCuentaEsco = getDetalleCuentaEscoPortOut.run(usuarioLogin);

		//TODO: validar Saldo: necesito el RATIO de la coti !!
		SaldoCuentaEsco saldoCuentaEsco = getSaldoCuentaEscoPortOut.run(usuarioLogin, detalleCuentaEsco.getComitente());
		
		
		
		Operacion operacion = crearOperacionRipioPortOut.run(dto);
		
		
		
		CrearComprobantePagoEscoDto comprobante = new CrearComprobantePagoEscoDto();
		BigDecimal importe = operacion.getActivoCotiCantidad().multiply(operacion.getRatio());
		comprobante.setImporte(importe);
		comprobante.setMoneda(operacion.getActivoCoti());
		
		comprobante.setCuenta(detalleCuentaEsco.getComitente());
		comprobante.setCodCtaBancariaComitente(null); //TODO: ver esto !!!
		
		crearComprobantePagoEscoPortOut.run(comprobante);
		
		operacion = operacionRepository.save(operacion);
		
		log.debug("outputParam -> {}", operacion);
		return operacion;
	}
	
}
