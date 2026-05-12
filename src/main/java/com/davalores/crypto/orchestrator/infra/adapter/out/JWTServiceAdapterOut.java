package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.util.Assert;

import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTServiceAdapterOut implements JWTServicePortOut {

	public static final String TOKEN_CLAIM_TYPE = "tokentype";

	private JWTServiceAdapterOut() {}
 
	public static Optional<Map<String, Object>> parseClaims(String token, String secret) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
			
			Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			
			//Map<String, Object> claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
			Assert.notNull(claims, "Token inválido");
			return Optional.of(claims);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public static String generate(Map<String, Object> claims, String subject, String secret, Duration expiration) {
		SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		Date expirationDate = generateExpirationDate(expiration);
		
		return Jwts.builder()
			.claims(claims)
			.subject(subject)
			.issuedAt(expirationDate)
			.expiration(expirationDate)
			.signWith(key)
			.compact();
		/*
		return Jwts.builder()
				.addClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
				.compact();*/
	}

	private static Date generateExpirationDate(Duration expiration) {
		return new Date(System.currentTimeMillis() + expiration.toMillis());
	}
}
