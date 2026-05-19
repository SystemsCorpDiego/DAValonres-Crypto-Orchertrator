package com.davalores.crypto.orchestrator.app.service.common.jwt;

public class JwtTokenData {
	
	public final TokenTipoEnum tipo;
	public final String usuario;
	public final Integer usuarioId;

	public JwtTokenData(TokenTipoEnum type, String usuario, Integer usuarioId) {
		this.tipo = type;
		this.usuario = usuario;
		this.usuarioId = usuarioId;
	}

	public boolean isType(TokenTipoEnum tipo) {
		return this.tipo == tipo;
	}
}
