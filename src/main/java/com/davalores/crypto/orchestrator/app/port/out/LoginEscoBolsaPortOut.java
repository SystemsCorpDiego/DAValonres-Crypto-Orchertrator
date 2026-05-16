package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;

public interface LoginEscoBolsaPortOut {

	public UsuarioEsco run(String usuario, String clave);
	
}
