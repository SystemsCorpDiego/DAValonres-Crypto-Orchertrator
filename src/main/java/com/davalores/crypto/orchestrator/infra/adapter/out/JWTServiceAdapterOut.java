package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.util.Assert;

import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JwtTokenData;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		} catch(ExpiredJwtException  ee) {
			throw new LoginException(ErrorCodeEnum.HTTP_LOGIN_TIMEOUT_ERROR.toString(), "Token expirado");
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
			.issuedAt( new Date(System.currentTimeMillis()) )
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
	
	public static boolean isTokenType(TokenTipoEnum expected, Map<String, Object> claims) {
		Object found = claims.get(TOKEN_CLAIM_TYPE);
		return (expected.toString().equals(found));
	}
	
	public static Optional<JwtTokenData> parseToken(String token, String secret, TokenTipoEnum expectedType) {
		
		Optional<Map<String, Object>> parseClaims = parseClaims(token, secret);
		if ( parseClaims.isPresent() ) {
			Optional<Map<String, Object>> aux = parseClaims.filter(claims -> isTokenType(expectedType, claims));			

			Optional<JwtTokenData> tokenData =
			aux.map( (claims) -> {
						log.debug("Claims found: {}", claims);
						
						return new JwtTokenData(
								expectedType,
								claims.get("sub").toString(),
								Integer.parseInt( claims.get("usuarioId").toString() )
								);
					} );
			
			if ( tokenData.isPresent() ) {
				return tokenData;
			}				
		}
		
		/*
		return parseClaims(token, secret)
				.filter(claims -> isTokenType(expectedType, claims))
				.map(
						claims -> new JwtTokenData(
								expectedType,
								claims.get("sub").toString(),
								(Integer)claims.get("usuarioId")
						)
				);
		*/
		return Optional.empty();
	}
}
