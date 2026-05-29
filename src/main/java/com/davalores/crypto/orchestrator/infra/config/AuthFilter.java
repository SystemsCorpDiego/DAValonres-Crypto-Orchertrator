package com.davalores.crypto.orchestrator.infra.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.app.service.usuario.GetTokenUsuarioService;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;
import com.davalores.crypto.orchestrator.domain.model.exception.LoginException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class AuthFilter implements Filter {

    private String tokenHeader;
	private final GetTokenUsuarioService getTokenUsuarioService;
	
	public AuthFilter(GetTokenUsuarioService getTokenUsuarioService, 
			@Value("${login.token.header}") String tokenHeader) {
		this.getTokenUsuarioService = getTokenUsuarioService;
		this.tokenHeader = tokenHeader;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		if ( httpRequest.getRequestURI().equals("/crypto/auth/login") ||
				httpRequest.getRequestURI().equals("/crypto/auth/login/2fa") ||
				httpRequest.getRequestURI().equals("/crypto/auth/login/token/refresh") ) {
			chain.doFilter(request, response);
            return; 
		}
		
		
		String token = extractToken(httpRequest);

        if (token != null) {
        	Usuario usuario = null;
        	try {
        		usuario = getTokenUsuarioService.run(token, TokenTipoEnum.NORMAL);
        	} catch ( Exception e) {
        		httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
                return;
        	}
            
            httpRequest.setAttribute("token", token);
            httpRequest.setAttribute("usuario", usuario.getUsuario());
            httpRequest.setAttribute("usuarioId", usuario.getId());
        }
        
        chain.doFilter(request, response);		
	}

	private String extractToken(HttpServletRequest request) {
        String header = request.getHeader( tokenHeader );        
		if (header == null || !header.startsWith("Bearer ") )
			throw new LoginException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe incluir los parametros de Login");

		String token = header.replaceFirst("^Bearer ", "");
        return token;
    }


}
