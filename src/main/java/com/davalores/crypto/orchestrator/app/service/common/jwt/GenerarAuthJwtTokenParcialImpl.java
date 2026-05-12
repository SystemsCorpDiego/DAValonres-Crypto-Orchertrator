package com.davalores.crypto.orchestrator.app.service.common.jwt;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;

@Service
public class GenerarAuthJwtTokenParcialImpl implements GenerarAuthJwtTokenParcial {
	
    private final String secret;
    private final Duration tokenExpiration;

    public GenerarAuthJwtTokenParcialImpl(
            @Value("${seguridad.token.secret}") String secret,
            @Value("${seguridad.token.expiration}") Duration tokenExpiration) {
        this.secret = secret;
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public JWTokenBo run(Integer userId, String username) {
        String token = createPartiallyAuthenticatedToken(userId, username);
        return new JWTokenBo(token, null);
    }

    private String createPartiallyAuthenticatedToken(Integer userId, String username) {
        Map<String, Object> claims = Map.of(
                "userId", userId,
                JWTServicePortOut.getTokenClaimType(), TokenTipoEnum.AUTENTICACION_PARCIAL
        );
        return JWTServicePortOut.generate(claims, username, secret, tokenExpiration);
    }
    
}
