package com.davalores.crypto.orchestrator.app.port.in.usuario;

import java.util.List;

import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface ConsultarUsuariosPortIn {

	public List<Usuario> run();
	
}
