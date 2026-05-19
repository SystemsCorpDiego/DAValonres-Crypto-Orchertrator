package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.ConfirmarDFATokenPortIn;
import com.davalores.crypto.orchestrator.infra.adapter.out.UsuarioJpaRepositoryAdapterOut;

@Service
public class ConfirmarDFATokenService implements ConfirmarDFATokenPortIn {

	private final UsuarioJpaRepositoryAdapterOut usuarioRepository;
	
	public ConfirmarDFATokenService(UsuarioJpaRepositoryAdapterOut usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
    public void run(Integer usuarioId, boolean habilitar) {
		
		usuarioRepository.saveConfirmarDfa(usuarioId, habilitar);
		
	}

}
