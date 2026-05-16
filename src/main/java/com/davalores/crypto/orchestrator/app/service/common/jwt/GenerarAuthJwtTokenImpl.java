package com.davalores.crypto.orchestrator.app.service.common.jwt;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;
import com.davalores.crypto.orchestrator.infra.adapter.out.JWTServiceAdapterOut;

@Service
public class GenerarAuthJwtTokenImpl implements GenerarAuthJwtToken {

    private final String secret;
    private final Duration tokenExpiration;
    private final Duration refreshTokenExpiration;

    
    public GenerarAuthJwtTokenImpl(
            @Value("${login.token.secreto}") String secret,
            @Value("${login.token.expiracion}") Duration tokenExpiration,
            @Value("${login.tokenRefresco.expiracion}") Duration refreshTokenExpiration) {
        this.secret = secret;
        this.tokenExpiration = tokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    @Override
    public JWTokenBo run(Integer userId, String username) { 
        String token = createNormalToken(userId, username);
        String refreshToken = createRefreshToken(username);
        return new JWTokenBo(token, refreshToken);
    }

    private String createRefreshToken(String username) {
        Map<String, Object> claims = Map.of(
        		JWTServicePortOut.getTokenClaimType(), TokenTipoEnum.REFRESH
        );
        return JWTServicePortOut.generate(claims, username, secret, refreshTokenExpiration);
    }

    private String createNormalToken(Integer userId, String username) {
        Map<String, Object> claims = Map.of(
                "usuarioId", userId,
                JWTServicePortOut.getTokenClaimType(), TokenTipoEnum.NORMAL
        );
        return JWTServicePortOut.generate(claims, username, secret, tokenExpiration);
    }

}
