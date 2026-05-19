package com.davalores.crypto.orchestrator.app.service.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.ConfirmarDFATokenPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JwtTokenData;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.infra.adapter.out.JWTServiceAdapterOut;
import com.davalores.crypto.orchestrator.infra.adapter.out.UsuarioJpaRepositoryAdapterOut;

@Service
public class ConfirmarDFATokenService implements ConfirmarDFATokenPortIn {

	private final String secreto;
	private final UsuarioJpaRepositoryAdapterOut usuarioRepository;
	
	public ConfirmarDFATokenService(UsuarioJpaRepositoryAdapterOut usuarioRepository,
			@Value("${login.token.secreto}") String secreto) {
		this.usuarioRepository = usuarioRepository;
		this.secreto = secreto;
	}
	
	@Override
    public void run(Integer usuarioId, boolean habilitar) {		
		usuarioRepository.saveConfirmarDfa(usuarioId, habilitar);		
	}

	@Override	
	public void run(String token, boolean habilitar) {
		Optional<JwtTokenData> jwtTokenData = JWTServiceAdapterOut.parseToken(token, secreto, TokenTipoEnum.NORMAL);
		run( jwtTokenData.get().getUsuarioId(), habilitar );
	}
}
