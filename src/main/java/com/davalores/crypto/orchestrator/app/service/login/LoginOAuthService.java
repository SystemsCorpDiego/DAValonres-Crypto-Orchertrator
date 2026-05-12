package com.davalores.crypto.orchestrator.app.service.login;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.in.login.LoginOAuthPortIn;
import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class LoginOAuthService implements LoginOAuthPortIn {
	//Este es como el LoginJWTImpl
	
	
	@Override
	public JWTokenBo run(String usuario, String clave) {
		JWTokenBo salida = null;

		if ( usuario == null)
			throw new LoginException(null, "Debe informar un Usuario");
		if ( clave == null)
			throw new LoginException(null, "Debe informar un clave");
		
		//TODO:
		//loguear a VisualBolsa via API Rest
			//Si loguea, solo levanto seteos de Middleware. 
		
		//Si no loguea VisualBolsa, loguear a Middleware.
			//validar pwd bloqueada, vencida, etc. => excepciones
			
		//	veo si debe loguear DFA => token parcial (GenerarAuthTokenParcial.run()) o token normal (GenerarAuthToken.run())
		if ( fetchUserHasTwoFactorAuthenticationEnabled.run(user.getId())) {
			//TODO: VER PORQUE NO ANDA!!!
			//result = generatePartiallyAuthenticationToken.run(user.getId(), user.getNombre());
			result = generateToken.generateTokens(user.getId(), user.getNombre());
		} else {
			result = generateToken.generateTokens(user.getId(), user.getNombre());
			usuarioInfoStorage.actualizarLoginDate(user.getNombre());
		}
		
		
		if ( salida == null)
			throw new LoginException(null, "Usuario o clave invalidos");
		
		log.debug("Token generated");
		return salida;
	}
	
}
