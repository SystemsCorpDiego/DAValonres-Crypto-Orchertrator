package com.davalores.crypto.orchestrator.app.service.usuario;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.UsuarioPortIn;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

@Service
public class UsuarioService implements UsuarioPortIn {

	private UsuarioRepositoryPortOut outPort;
	
	public UsuarioService(UsuarioRepositoryPortOut repository) {
		this.outPort = repository;
	}
	
	@Override
	public Usuario get(Integer id) {
		// TODO Auto-generated method stub
		
		return outPort.get(id);
	}

}
