package com.davalores.crypto.orchestrator.app.service.trade;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearOperacionPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CrearOperacionRipioPortOut;
import com.davalores.crypto.orchestrator.app.port.out.OperacionRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.app.service.usuario.GetTokenUsuarioService;
import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearOperacionService implements CrearOperacionPortIn {

	private final OperacionRepositoryPortOut operacionRepository;
	private final CrearOperacionRipioPortOut crearOperacionRipioPortOut;
	private final GetTokenUsuarioService getTokenUsuarioService;
	
	public CrearOperacionService( 
			CrearOperacionRipioPortOut crearOperacionRipioPortOut, 
			OperacionRepositoryPortOut operacionRepository,
			GetTokenUsuarioService getTokenUsuarioService) {
		this.operacionRepository = operacionRepository;
		this.crearOperacionRipioPortOut = crearOperacionRipioPortOut;
		this.getTokenUsuarioService = getTokenUsuarioService;
	}
	
	
	@Override
	public Operacion run(String authToken, CrearOperacion dto) {
		// TODO sacar usuarioId del token, recuperar ripio_id del usuario,  y luego llamar a Ripio para crear la operacion
		log.debug("inputParam -> authToken: {} - dto: {}", authToken, dto);
		
		Usuario usuarioLogin = getTokenUsuarioService.run(authToken, TokenTipoEnum.NORMAL);		
		
		if ( usuarioLogin.getRipioId() == null )
			throw new BusinessException(ErrorCodeEnum.CONFIGURATION_ERROR.toString(), "El usuario no tiene asociado un RipioId");
		dto.setRipioId(usuarioLogin.getRipioId());

		Operacion operacion = crearOperacionRipioPortOut.run(dto);
		
		operacion = operacionRepository.save(operacion);
		
		log.debug("outputParam -> {}", operacion);
		return operacion;
	}
	
	
}
