package com.davalores.crypto.orchestrator.app.service.login;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.davalores.crypto.orchestrator.app.port.out.JWTServicePortOut;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;

@Component
public class GenerarTokenServiceImpl implements GenerarTokenService {

    private final String secreto;
    private final Duration tokenExpira;
    private final Duration tokenRefrescoExpira;    
    
    public GenerarTokenServiceImpl(
            @Value("${login.token.secreto}") String secreto,
            @Value("${login.token.expiracion}") Duration tokenExpira,
            @Value("${login.tokenRefresco.expiracion}") Duration tokenRefrescoExpira) {
        this.secreto = secreto;
        this.tokenExpira = tokenExpira;
        this.tokenRefrescoExpira = tokenRefrescoExpira;
    }

	@Override
	public JWTokenBo run(String usuarioId, String username) {
		String token = crearTokenNormal(usuarioId, username);
        String refreshToken = crearTokenRefresco(username);
        return new JWTokenBo(token, refreshToken);
	}
	
	@Override
	public JWTokenBo runParcial(String userId, String username) {
		String token = createTokenParcial(userId, username);
        return new JWTokenBo(token, null);
	}

    private String crearTokenRefresco(String username) {
        Map<String, Object> claims = Map.of(
        		JWTServicePortOut.getTokenClaimType(), TokenTipoEnum.REFRESH
        );
        return JWTServicePortOut.generate(claims, username, secreto, tokenRefrescoExpira);
    }

    private String crearTokenNormal(String usuarioId, String username) {
        Map<String, Object> claims = Map.of(
                "usuarioId", usuarioId,
                JWTServicePortOut.getTokenClaimType(), TokenTipoEnum.NORMAL
        );
        return JWTServicePortOut.generate(claims, username, secreto, tokenExpira);
    }

    private String createTokenParcial(String usuarioId, String username) {
        Map<String, Object> claims = Map.of(
                "usuarioId", usuarioId,
                JWTServicePortOut.getTokenClaimType(), TokenTipoEnum.AUTENTICACION_PARCIAL
        );
        return JWTServicePortOut.generate(claims, username, secreto, tokenExpira);
    }
    
}
