package com.davalores.crypto.orchestrator.app.service.common.jwt;


public class JWTokenBo {

	public final String token;
	public final String tokenRefresco;

	public JWTokenBo(String token, String tokenRefresco) {
		this.token = token;
		this.tokenRefresco = tokenRefresco;
	}

	public String getToken() {
		return token;
	}

	public String getTokenRefresco() {
		return tokenRefresco;
	}


	
}
