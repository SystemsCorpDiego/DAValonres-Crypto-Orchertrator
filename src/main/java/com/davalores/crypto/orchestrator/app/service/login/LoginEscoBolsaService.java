package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.LoginEscoBolsaPortOut;
import com.davalores.crypto.orchestrator.domain.model.UsuarioEsco;

@Service
public class LoginEscoBolsaService  {

	private final LoginEscoBolsaPortOut loginEscoBolsaPortOut;
	
	public LoginEscoBolsaService(LoginEscoBolsaPortOut loginEscoBolsaPortOut) {
		super();
		this.loginEscoBolsaPortOut = loginEscoBolsaPortOut;
	}
	
	
	public UsuarioEsco run(String usuario, String clave) {

		UsuarioEsco usuarioEsco = loginEscoBolsaPortOut.run(usuario, clave);
		
		return usuarioEsco;
	}
	

}
