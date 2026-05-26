package com.davalores.crypto.orchestrator.app.service.usuario;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.usuario.ConsultarUsuariosPortIn;
import com.davalores.crypto.orchestrator.app.port.out.UsuarioRepositoryPortOut;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

@Service
public class ConsultarUsuariosService implements ConsultarUsuariosPortIn {

	private final UsuarioRepositoryPortOut porOut;
	
	public ConsultarUsuariosService(UsuarioRepositoryPortOut porOut) {
		this.porOut = porOut;
	}
	
	@Override
	public java.util.List<Usuario> run() {
		
		return porOut.getAll();
		
	}

}
