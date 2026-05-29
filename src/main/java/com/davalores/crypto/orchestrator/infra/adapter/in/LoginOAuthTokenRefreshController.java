package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthTokenRefreshPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.TokenOauthRefreshDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "1) Login Auth", description = " ")
@SecurityRequirement(name = "basicAuth")

@Slf4j
@RestController
@RequestMapping("/auth/login/token/refresh")
public class LoginOAuthTokenRefreshController {

	private final LoginOAuthTokenRefreshPortIn portIn;
	
	public LoginOAuthTokenRefreshController(LoginOAuthTokenRefreshPortIn portIn) {
		this.portIn = portIn;
	}

	@Operation(summary = "Login OAuth Basic - Refresh de los token por TimeOut" )		

	@PostMapping
	public ResponseEntity<JWTokenBo> run(@RequestBody TokenOauthRefreshDto dto) {
		log.debug("inputParam -> {}", dto);
		
		JWTokenBo tokens = portIn.run(dto.getTokenRefresco());		
		
		log.debug("outputParam -> {}", tokens);
		return ResponseEntity.ok(tokens);
	}
}
