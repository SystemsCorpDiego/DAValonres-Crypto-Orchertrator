package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.domain.model.DetalleCuentaEsco;
import com.davalores.crypto.orchestrator.domain.model.Usuario;

@Service
public class GetDetalleCuentaEscoService {

	private final LoginEscoBolsaPortOut loginEscoBolsa;
	
	public GetDetalleCuentaEscoService(LoginEscoBolsaPortOut loginEscoBolsa) {
		this.loginEscoBolsa = loginEscoBolsa;
	}
	
	public DetalleCuentaEsco run(String usuario) {
		DetalleCuentaEsco detalle = new DetalleCuentaEsco();
		
		return detalle; 
	}
	
}
