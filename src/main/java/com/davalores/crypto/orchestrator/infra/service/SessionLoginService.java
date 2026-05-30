package com.davalores.crypto.orchestrator.infra.service;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class SessionLoginService {

	public Usuario getUsuario(HttpServletRequest request) {
		Usuario usuarioLogin = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Object attr = request.getAttribute("usuario");
			usuarioLogin = objectMapper.readValue(attr.toString(), Usuario.class);
		} catch(Exception e) {}
		
		if ( usuarioLogin == null )
			throw new LoginException(ErrorCodeEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "Error de login");	
		
		return usuarioLogin;
	}

	public void saveLogin(HttpServletRequest request, String token, Usuario usuario) {
		try {
        	ObjectMapper objectMapper = new ObjectMapper();
        	String serializedUsuario = objectMapper.writeValueAsString(usuario);
            
        	
        	request.setAttribute("token", token);
        	request.setAttribute("usuario", serializedUsuario);
			
		} catch(Exception e) {
			throw new LoginException(ErrorCodeEnum.HTTP_UNAUTHORIZED_ERROR.toString(), "Error de login");	
		}
	}
}
