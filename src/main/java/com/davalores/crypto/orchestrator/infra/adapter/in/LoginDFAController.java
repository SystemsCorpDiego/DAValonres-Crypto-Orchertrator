package com.davalores.crypto.orchestrator.infra.adapter.in;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginDFAPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.app.service.login.LoginDFAService;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DFACodeDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.OauthTokenResponseDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.LoginMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("auth/login/2fa")
public class LoginDFAController {

	private final String tokenHeader; 
	private final LoginMapper mapper;
	private final LoginDFAPortIn loginDFA;
	
	public LoginDFAController(LoginDFAPortIn loginDFA,
			@Value("${login.token.header}") String tokenHeader, LoginMapper mapper) {
		this.tokenHeader = tokenHeader;
		this.mapper = mapper;
		this.loginDFA = loginDFA;
	}
	
	@PostMapping
	public ResponseEntity<OauthTokenResponseDto>  run(HttpServletRequest request, @RequestBody DFACodeDto dto) {
		log.debug("inputParam -> {}", dto);
		OauthTokenResponseDto response = null;
		//request: recupero token y saco usuario
		String token = getAuthToken(request);
		
		//llamo al caso de uso  
		JWTokenBo reg = loginDFA.run(token, dto.getCodigo());
		response = mapper.run(reg);
		
		log.debug("outputParam -> {}", response);
		//devuelvo nuevo token 
		return ResponseEntity.ok(response);
	}

	private String getAuthToken(HttpServletRequest request) {
		// recupero token del header Authorization
		String auth = request.getHeader( tokenHeader );
		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		
		return token;
		
	}
}
