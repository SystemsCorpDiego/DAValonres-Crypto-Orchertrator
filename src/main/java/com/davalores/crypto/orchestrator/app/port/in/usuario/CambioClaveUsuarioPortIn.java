package com.davalores.crypto.orchestrator.app.port.in.usuario;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CambioClaveUsuarioDto;

public interface CambioClaveUsuarioPortIn {

	public void run(Usuario usuarioLogin, Integer usuarioId, CambioClaveUsuarioDto dto);
	
}
