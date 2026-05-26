package com.davalores.crypto.orchestrator.app.port.in.usuario;

import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CambioClaveUsuarioDto;

public interface CambioClaveUsuarioPortIn {

	public void run(String token, Integer usuarioId, CambioClaveUsuarioDto dto);
	
}
