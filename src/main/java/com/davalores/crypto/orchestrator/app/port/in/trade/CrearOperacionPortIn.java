package com.davalores.crypto.orchestrator.app.port.in.trade;

import com.davalores.crypto.orchestrator.domain.model.CrearOperacion;
import com.davalores.crypto.orchestrator.domain.model.Operacion;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface CrearOperacionPortIn {
	
	public Operacion run(Usuario usuarioLogin, CrearOperacion dto);	

}
