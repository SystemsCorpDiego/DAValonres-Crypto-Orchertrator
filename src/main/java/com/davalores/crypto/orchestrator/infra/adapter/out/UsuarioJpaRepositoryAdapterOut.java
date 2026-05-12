package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.stereotype.Repository;

import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

@Repository
public class UsuarioJpaRepositoryAdapterOut implements UsuarioRepositoryPortOut {

	//private UsuarioBoEntityMapper mapper;
	//private UsuarioJpaRepository repository;

	
	@Override
	public Usuario get(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
