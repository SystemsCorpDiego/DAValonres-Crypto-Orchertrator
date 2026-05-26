package com.davalores.crypto.orchestrator.app.service.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.service.common.jwt.JwtTokenData;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.infra.adapter.out.JWTServiceAdapterOut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GetLoginTokenUsuarioId {
	private final String secreto;
	
	public GetLoginTokenUsuarioId(@Value("${login.token.secreto}") String secreto) {
		this.secreto = secreto;
	}
	
	public Integer run(String token) {		
		return run(token, TokenTipoEnum.NORMAL);
	}
	
	public Integer run(String token, TokenTipoEnum tokenTipo) {
		log.debug("inputParam -> {}", token);
		
		Optional<JwtTokenData> jwtTokenData = JWTServiceAdapterOut.parseToken(token, secreto, tokenTipo);
		if (!jwtTokenData.isPresent())
			throw new LoginException(ErrorCodeEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "Token invalido (1)");			
	
		Integer usuarioId = jwtTokenData.get().getUsuarioId();
		
		log.debug("outputParam -> {}", usuarioId);
		return usuarioId;		
	}
	
}
