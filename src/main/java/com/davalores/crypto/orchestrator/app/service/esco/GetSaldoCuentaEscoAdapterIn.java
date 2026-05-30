package com.davalores.crypto.orchestrator.app.service.esco;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.esco.GetSaldoCuentaEscoPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetDetalleCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetSaldoCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetSaldoCuentaEscoAdapterIn implements GetSaldoCuentaEscoPortIn {

	private final LoginEscoBolsaPortOut loginEscoBolsa;
	private final GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut;
	private final GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut;
	private final CypherServicePortOut cypherServicePortOut;
	
	public GetSaldoCuentaEscoAdapterIn(
			LoginEscoBolsaPortOut loginEscoBolsa,
			GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut,
			GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut,
			CypherServicePortOut cypherServicePortOut) {
		this.loginEscoBolsa = loginEscoBolsa;
		this.getDetalleCuentaEscoPortOut = getDetalleCuentaEscoPortOut;
		this.getSaldoCuentaEscoPortOut = getSaldoCuentaEscoPortOut;
		this.cypherServicePortOut = cypherServicePortOut;
	}
	
	@Override
	public SaldoCuentaEsco run(Usuario usuarioLogin) {
		log.debug("inputParam => {}", usuarioLogin);
		SaldoCuentaEsco dto = null;
		
		// 1- recupero clave esco
		if ( usuarioLogin.getEscoId() == null || usuarioLogin.getClaveEsco() == null )	
			throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "El Usuario no pertenece al sistema ESCO");
			
		// 2 - login a esco
		String clave = cypherServicePortOut.desencriptar( usuarioLogin.getClaveEsco() );
		
		UsuarioEsco usuarioEsco = loginEscoBolsa.run(usuarioLogin.getUsuario(), clave );
		
		// 3 - ejecuto detalle cuenta
		DetalleCuentaEsco detalleCuentaEsco = getDetalleCuentaEscoPortOut.run(usuarioEsco.getAccessToken());
		
		// 4 - ejecuto tenencia
		SaldoCuentaEsco saldoCuentaEsco = getSaldoCuentaEscoPortOut.run(usuarioEsco.getAccessToken(), detalleCuentaEsco.getComitente());
		
		dto = new SaldoCuentaEsco();
		dto.setCantidad(saldoCuentaEsco.getCantidad());
		dto.setComitente(saldoCuentaEsco.getComitente());
		
		log.debug("outputParam => {}", dto);
		return dto;
	}

}
