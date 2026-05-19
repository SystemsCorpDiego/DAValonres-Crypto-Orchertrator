package com.davalores.crypto.orchestrator.app.port.in.login;

public interface ConfirmarDFATokenPortIn {

	public void run(Integer usuarioId, boolean habilitar);
	public void run(String token, boolean habilitar);
	
}
