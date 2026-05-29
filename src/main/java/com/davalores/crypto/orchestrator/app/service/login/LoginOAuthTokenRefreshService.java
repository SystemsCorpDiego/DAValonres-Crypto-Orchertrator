package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthTokenRefreshPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.app.service.common.jwt.TokenTipoEnum;
import com.davalores.crypto.orchestrator.app.service.usuario.GetTokenUsuarioService;
import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.domain.model.exception.BusinessException;
import com.davalores.crypto.orchestrator.domain.model.exception.ErrorCodeEnum;

@Service
public class LoginOAuthTokenRefreshService implements LoginOAuthTokenRefreshPortIn {

	private final  GetTokenUsuarioService getTokenUsuarioService;
	private final GenerarTokenService generarToken;
	
	public LoginOAuthTokenRefreshService(GenerarTokenService generarToken,
			GetTokenUsuarioService getTokenUsuarioService) {
		this.generarToken = generarToken;
		this.getTokenUsuarioService = getTokenUsuarioService;
	}
	
	public JWTokenBo run(String tokenRefresh) {
		JWTokenBo token = null;
		
		if (ObjectUtils.isEmpty(tokenRefresh)) {
			throw  new BusinessException(ErrorCodeEnum.INPUT_PARAM_REQUIRED_ERROR.toString(), "Debe informar un Token para refrescar el login");
        }
		
		Usuario usuario = getTokenUsuarioService.run(tokenRefresh, TokenTipoEnum.REFRESH);
		token = generarToken.run(usuario.getId().toString(), usuario.getUsuario());
		
		return token ;
	}

}
