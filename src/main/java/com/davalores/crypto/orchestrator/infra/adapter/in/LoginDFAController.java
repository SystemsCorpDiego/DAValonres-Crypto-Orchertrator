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
import com.davalores.crypto.orchestrator.domain.model.TokenOauth;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.DFACodeDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.mapper.LoginMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "1) Login Auth", description = " ")
@SecurityRequirement(name = "basicAuth")

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
	
	
	@Operation(summary = "Login 2FA " )	
	
	@PostMapping
	public ResponseEntity<TokenOauth>  run(HttpServletRequest request, @RequestBody DFACodeDto dto) {
		log.debug("inputParam -> {}", dto);
		TokenOauth response = null;
		
		if ( dto == null || dto.getCodigo() == null)
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir un codigo de validacion de 2FA");
		
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
		if (auth == null)
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");
		

		//String token = auth.split(" ")[1];
		String token = auth.replaceFirst("^Bearer ", "");
		if (token == null)
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");
		
		
		return token;
		
	}
}
