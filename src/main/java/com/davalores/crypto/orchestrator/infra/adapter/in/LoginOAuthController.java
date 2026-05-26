package com.davalores.crypto.orchestrator.infra.adapter.in;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.domain.model.LoginOauth;
import com.davalores.crypto.orchestrator.domain.model.TokenOauth;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.LoginMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/login")
public class LoginOAuthController {

	private String tokenHeader;
	private LoginMapper mapper;
	
	private final LoginOAuthPortIn loginOAuth;
	
	public LoginOAuthController(LoginOAuthPortIn loginOAuth, @Value("${login.token.header}") String tokenHeader, LoginMapper mapper) {
		this.loginOAuth = loginOAuth;
		this.mapper = mapper;
		this.tokenHeader = tokenHeader;
	}
		
	
	@PostMapping
	public ResponseEntity<LoginOauth>  run(HttpServletRequest request) {
		log.debug("run -> ");
		TokenOauth response = null;

		//saco usuario y clave
		String usuario = getUsuarioFromRequest(request);
		String clave = getClaveFromRequest(request);
		log.debug("run -> usuario: {} clave: {}", usuario, clave);

		if (usuario == null || usuario.isBlank()) 
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un Usuario y Clave de Login");
		if (clave == null || clave.isBlank()) 
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un Usuario y Clave de Login");

		
		//llamo al caso de uso 		
		LoginOauth dto = loginOAuth.run(usuario, clave);		
		//response = mapper.run(dto);
		
		log.debug("outputParam -> dto: {}", dto);
		return ResponseEntity.ok(dto);
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
		
		String auth = request.getHeader(tokenHeader);
		if (auth == null)
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");
		
		String[] parts = auth.split(" ");
		if (parts == null || parts.length != 2) 
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");
		if (parts[1] == null ) 
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");


		
		byte[] decodedBytes = Base64.getDecoder().decode(parts[1]);
		String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
        return decodedString;
    }
	
}
