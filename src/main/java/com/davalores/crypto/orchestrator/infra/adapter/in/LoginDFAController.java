package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginDFAPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DFACodeDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.OauthTokenResponseDto;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth/login/dfa")
public class LoginDFAController {

	private LoginMapper mapper;
	private final LoginDFAPortIn loginDFA;
	
	public LoginDFAController(LoginDFAPortIn loginDFA) {
		this.loginDFA = loginDFA;
	}
	
	@PostMapping
	public ResponseEntity<OauthTokenResponseDto>  run(HttpServletRequest request, @RequestBody DFACodeDto dto) {
		OauthTokenResponseDto response = null;
		//request: recupero token y saco usuario
		String token = getAuthToken(request);
		
		//llamo al caso de uso  
		JWTokenBo reg = loginDFA.run(token, dto.getValue());
		response = mapper.run(reg);
		
		//devuelvo nuevo token 
		return ResponseEntity.ok(response);
	}

	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader("Authorization");
		String token = auth.split(" ")[1];
		
		return token;
	}
}
