package com.davalores.crypto.orchestrator.infra.adapter.in;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.OauthTokenResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth/login/")
public class LoginOAuthController {

	private LoginMapper mapper;
	
	private final LoginOAuthPortIn loginOAuth;
	
	public LoginOAuthController(LoginOAuthPortIn loginOAuth) {
		this.loginOAuth = loginOAuth;
	}
		
	
	@GetMapping
	public ResponseEntity<OauthTokenResponseDto>  run(HttpServletRequest request) {
		OauthTokenResponseDto response = null;

		//saco usuario y clave
		String usuario = getUsuarioFromRequest(request);
		String clave = getClaveFromRequest(request);
		
		//llamo al caso de uso 		
		JWTokenBo dto = loginOAuth.run(usuario, clave);		
		response = mapper.run(dto);
		
		return ResponseEntity.ok(response);
	}
	
	
	private String getClaveFromRequest(HttpServletRequest request) {
		String decodedString = decodeAuth(request);
		String[] parts = decodedString.split(":");
		return parts[1];
	}

	private String getUsuarioFromRequest(HttpServletRequest request) {
		String decodedString = decodeAuth(request);
		String[] parts = decodedString.split(":");
		return parts[0];
	}
	
	private String decodeAuth(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");
		String[] parts = auth.split(" ");
		
		byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
		String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedString;
    }
	
}
