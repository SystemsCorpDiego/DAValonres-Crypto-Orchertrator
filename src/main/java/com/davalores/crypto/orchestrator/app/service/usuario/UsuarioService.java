package com.davalores.crypto.orchestrator.app.service.usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.UsuarioPortIn;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;

@Service
public class UsuarioService implements UsuarioPortIn {

	private UsuarioRepositoryPortOut outPort;
	
	public UsuarioService(UsuarioRepositoryPortOut repository) {
		this.outPort = repository;
	} 
	
	@Override
	public Usuario get(Integer id) {
		// TODO Auto-generated method stub
		Optional<Usuario> usuario = outPort.findById(id);
		if ( usuario.isEmpty() )
			throw new BusinessException("Usuario no encontrado para o id: " + id);
					
		return usuario.get();
	}

}
