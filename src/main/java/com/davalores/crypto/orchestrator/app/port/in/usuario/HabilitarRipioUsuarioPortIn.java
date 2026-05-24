package com.davalores.crypto.orchestrator.app.port.in.usuario;

import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface HabilitarRipioUsuarioPortIn {

	Usuario run(Integer usuarioId, Boolean habilitar);
	
}
