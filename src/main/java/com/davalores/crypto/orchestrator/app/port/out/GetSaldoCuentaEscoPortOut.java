package com.davalores.crypto.orchestrator.app.port.out;

import com.davalores.crypto.orchestrator.domain.model.SaldoCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface GetSaldoCuentaEscoPortOut {

	public SaldoCuentaEsco run(String token, Long comitente); 
	public SaldoCuentaEsco run(String usuarioEsco, String clave, Long comitente);
	public SaldoCuentaEsco run(Usuario usuarioLogin, Long comitente);
	
}
