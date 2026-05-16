package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

@Repository
public class UsuarioJpaRepositoryAdapterOut implements UsuarioRepositoryPortOut {

	//private UsuarioBoEntityMapper mapper;
	//private UsuarioJpaRepository repository;
	private final UsuarioJpaRepository repository;
	
	public UsuarioJpaRepositoryAdapterOut(UsuarioJpaRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Usuario get(Integer id) {
		// TODO Auto-generated method stub
		Usuario usuario = repository.getReferenceById(id);
		return usuario;
	}

	@Override
	public Optional<Usuario> getByUsuario(String usuaDescrip) {
		
		Optional<Usuario> usuario = repository.findByDescripcion(usuaDescrip);
		return usuario;
	}

	
}
