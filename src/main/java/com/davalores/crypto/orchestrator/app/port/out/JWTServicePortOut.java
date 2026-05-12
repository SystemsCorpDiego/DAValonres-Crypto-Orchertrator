package com.davalores.crypto.orchestrator.app.port.out;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

import com.davalores.crypto.orchestrator.infra.adapter.out.JWTServiceAdapterOut;

public interface JWTServicePortOut {

	public static Optional<Map<String, Object>> parseClaims(String token, String secret) {
		return JWTServiceAdapterOut.parseClaims(token, secret);
	}
	
	public static String generate(Map<String, Object> claims, String subject, String secret, Duration expiration) {
		return JWTServiceAdapterOut.generate(claims, subject, secret, expiration);
	}
	
	public static String getTokenClaimType() {
		return JWTServiceAdapterOut.TOKEN_CLAIM_TYPE;		
	}
		
}
