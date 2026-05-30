package com.davalores.crypto.orchestrator.app.port.in.esco;

import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface GetSaldoCuentaEscoPortIn {

	public SaldoCuentaEsco run(Usuario usuarioLogin);
	
}
