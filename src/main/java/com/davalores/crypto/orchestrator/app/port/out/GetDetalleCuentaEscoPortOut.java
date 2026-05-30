package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface GetDetalleCuentaEscoPortOut {

	public DetalleCuentaEsco run(String token);
	public DetalleCuentaEsco run(String usuario, String clave);
	public DetalleCuentaEsco run(Usuario usuario);
	
}
