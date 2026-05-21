package com.davalores.crypto.orchestrator.app.service.trade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.trade.CrearOperacionPortIn;
import com.davalores.crypto.orchestrator.app.port.out.CrearOperacionRipioPortOut;
import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;
import com.davalores.crypto.orchestrator.app.port.out.OperacionRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JwtTokenData;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CrearOperacionService implements CrearOperacionPortIn {

	private final String secreto;
	private final UsuarioRepositoryPortOut usuarioRepository;
	private final OperacionRepositoryPortOut operacionRepository;
	private final CrearOperacionRipioPortOut crearOperacionRipioPortOut;
	
	public CrearOperacionService(@Value("${login.token.secreto}") String secreto, 
			UsuarioRepositoryPortOut usuarioRepository, CrearOperacionRipioPortOut crearOperacionRipioPortOut, OperacionRepositoryPortOut operacionRepository) {
		this.secreto = secreto;
		this.usuarioRepository = usuarioRepository;
		this.operacionRepository = operacionRepository;
		this.crearOperacionRipioPortOut = crearOperacionRipioPortOut;
	}
	
	
	@Override
	public Operacion run(String authToken, CrearOperacion dto) {
		// TODO sacar usuarioId del token, recuperar ripio_id del usuario,  y luego llamar a Ripio para crear la operacion
		log.debug("run -> dto: {}", dto);
		
		//Valida que sea TokenTipoEnum.AUTENTICACION_PARCIAL y recupera el usuarioId del claim
		Optional<JwtTokenData> jwtTokenData = JWTServicePortOut.parseToken(authToken, secreto, TokenTipoEnum.NORMAL);		
		if ( !jwtTokenData.isPresent() )
			throw new BusinessException("Token invalido (1) para crear operacion");
		dto.setUsuarioId(jwtTokenData.get().usuarioId);
		
		Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
				.orElseThrow(() -> new BusinessException("Token invalido (2) para crear operacion"));
		
		if ( usuario.getRipioId() == null )
			throw new BusinessException("El usuario no tiene asociado un RipioId");
		dto.setRipioId(usuario.getRipioId());

		Operacion operacion = crearOperacionRipioPortOut.run(dto);
		
		operacion = operacionRepository.save(operacion);
		
		return operacion;
	}
	
	
}
