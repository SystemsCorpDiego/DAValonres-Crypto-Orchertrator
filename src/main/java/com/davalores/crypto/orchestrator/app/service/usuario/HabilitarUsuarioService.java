package com.davalores.crypto.orchestrator.app.service.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.HabilitarUsuarioPortIn;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

@Service
public class HabilitarUsuarioService implements HabilitarUsuarioPortIn {

	private final UsuarioRepositoryPortOut repository;
	
	public HabilitarUsuarioService(UsuarioRepositoryPortOut repository) {
		this.repository = repository;
	}
	
	@Override
	public Usuario run(Integer usuarioId, Boolean habilitar) {

		Optional<Usuario> usuario = repository.findById(usuarioId);
		if ( usuario.isEmpty() )
			throw new BusinessException(ErrorCodeEnum.NO_DATA_FOUND_ERROR.toString(), "Usuario no encontrado");

		if ( habilitar && usuario.get().getHabilitado() == habilitar )
			throw new BusinessException(ErrorCodeEnum.BUSINESS_ERROR.toString(), "El Usuario ya se encuentra Habilitado");
		
		if ( !habilitar && !usuario.get().getHabilitado() == !habilitar )
			throw new BusinessException(ErrorCodeEnum.BUSINESS_ERROR.toString(), "El Usuario ya se encuentra Deshabilitado");
		
		usuario.get().setHabilitado(habilitar);
		
		Usuario usuarioNew = repository.save(usuario.get());
		
		return usuarioNew;
	}

}
