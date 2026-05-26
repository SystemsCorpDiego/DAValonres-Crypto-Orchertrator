package com.davalores.crypto.orchestrator.app.service.esco;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.esco.GetSaldoCuentaEscoPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetDetalleCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.GetSaldoCuentaEscoPortOut;
import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.login.GetLoginTokenUsuarioId;
import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetSaldoCuentaEscoAdapterIn implements GetSaldoCuentaEscoPortIn {

	private final UsuarioRepositoryPortOut usuarioRepository;
	private final GetLoginTokenUsuarioId getLoginTokenUsuarioId;
	private final LoginEscoBolsaPortOut loginEscoBolsa;
	private final GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut;
	private final GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut;
	private final CypherServicePortOut cypherServicePortOut;
	
	public GetSaldoCuentaEscoAdapterIn(UsuarioRepositoryPortOut usuarioRepository,
			GetLoginTokenUsuarioId getLoginTokenUsuarioId,
			LoginEscoBolsaPortOut loginEscoBolsa,
			GetDetalleCuentaEscoPortOut getDetalleCuentaEscoPortOut,
			GetSaldoCuentaEscoPortOut getSaldoCuentaEscoPortOut,
			CypherServicePortOut cypherServicePortOut) {
		this.usuarioRepository = usuarioRepository;
		this.getLoginTokenUsuarioId = getLoginTokenUsuarioId;
		this.loginEscoBolsa = loginEscoBolsa;
		this.getDetalleCuentaEscoPortOut = getDetalleCuentaEscoPortOut;
		this.getSaldoCuentaEscoPortOut = getSaldoCuentaEscoPortOut;
		this.cypherServicePortOut = cypherServicePortOut;
	}
	
	@Override
	public SaldoCuentaEsco run(String token) {
		log.debug("inputParam => {}", token);
		SaldoCuentaEsco dto = null;
		
		// 1- recupero clave esco
		Integer usuarioId = getLoginTokenUsuarioId.run(token);
		Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
		if ( usuario.isEmpty() )
			throw new LoginException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "Usuario o clave inválidos (1)");
		if ( usuario.get().getEscoId() == null || usuario.get().getClaveEsco() == null )	
			throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "El Usuario no pertenece al sistema ESCO");
			
		// 2 - login a esco
		String clave = cypherServicePortOut.desencriptar( usuario.get().getClaveEsco() );
		
		UsuarioEsco usuarioEsco = loginEscoBolsa.run(usuario.get().getUsuario(), clave );
		
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
