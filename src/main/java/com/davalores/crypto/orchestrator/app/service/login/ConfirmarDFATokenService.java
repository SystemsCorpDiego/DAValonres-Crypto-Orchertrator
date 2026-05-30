package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.ConfirmarDFATokenPortIn;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.out.UsuarioJpaRepositoryAdapterOut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfirmarDFATokenService implements ConfirmarDFATokenPortIn {

	private final UsuarioJpaRepositoryAdapterOut usuarioRepository;
	private final GetLoginTokenUsuarioId getLoginTokenUsuarioId;
	
	public ConfirmarDFATokenService(UsuarioJpaRepositoryAdapterOut usuarioRepository,
			GetLoginTokenUsuarioId getLoginTokenUsuarioId) {
		this.usuarioRepository = usuarioRepository;
		this.getLoginTokenUsuarioId = getLoginTokenUsuarioId;
	}
	
	@Override
    public void run(Integer usuarioId, boolean habilitar) {		
		log.debug("inputParam -> usuarioId: {} habilitar: {}", usuarioId, habilitar);
		usuarioRepository.saveConfirmarDfa(usuarioId, habilitar);		
		log.debug("outputParam -> null");
	}

	@Override	
	public void run(String token, boolean habilitar) {
		log.debug("inputParam -> token: {} habilitar: {}", token, habilitar);
		Integer usuarioId = getLoginTokenUsuarioId.run(token);
		run( usuarioId, habilitar );
	}
}
