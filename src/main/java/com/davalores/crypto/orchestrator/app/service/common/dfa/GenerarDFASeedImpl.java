package com.davalores.crypto.orchestrator.app.service.common.dfa;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenerarDFASeedImpl implements GenerarDFASeed {
	private final MessageSource messageSource;
	//private final UsuarioAuthenticationStorage userAuthenticationStorage;
	private final CypherServicePortOut cypher;
	private final String issuer;

	public GenerarDFASeedImpl(//UsuarioAuthenticationStorage userAuthenticationStorage,
										   CypherServicePortOut cypher,
										   @Value("${app.seguridad.dfa.issuer:EMPLE}") String issuer,
										   MessageSource messageSource) {
		//this.userAuthenticationStorage = userAuthenticationStorage;
		this.cypher = cypher;
		this.issuer = issuer;
		this.messageSource = messageSource;
	}

	public DFABo run(String username) {
		log.debug("Set two factor authentication");
		/*
		if (userAuthenticationStorage.userHasTwoFactorAuthenticationEnabled(usuarioId)) {
			String errorMsg = messageSource.getMessage(DFAExceptionEnum.DFA_HABILITADO_PREVIAMENTE.getMsgKey(), null, new Locale("es"));	
			throw new BusinessException(DFAExceptionEnum.DFA_HABILITADO_PREVIAMENTE.name(), errorMsg);
		}
		*/
		//String username = userAuthenticationStorage.getUsername(usuarioId);
		String secretKey = this.generateSecretKey();
		String encryptedSecret = cypher.encriptar(secretKey);
		
		//userAuthenticationStorage.setTwoFactorAuthenticationSecret(usuarioId, encryptedSecret);
		DFABo dfaBo = new DFABo.Builder()
				.account(username)
				.issuer(issuer)
				.sharedSecret(secretKey)
				.build(); 
		dfaBo.setAuthenticatorBarCode(generateAuthenticatorBarCode(dfaBo));
		
		log.debug("Output -> {}", dfaBo);
		return dfaBo;
	}
	
	private String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[20];
		random.nextBytes(bytes);
		Base32 base32 = new Base32();
		return base32.encodeToString(bytes);
	}
	
	private String generateAuthenticatorBarCode(DFABo dfa) {
		return "otpauth://totp/"
				+ URLEncoder.encode(dfa.getIssuer() + ":" + dfa.getAccount(), StandardCharsets.UTF_8).replace("+", "%20")
				+ "?secret=" + URLEncoder.encode(dfa.getSharedSecret(), StandardCharsets.UTF_8).replace("+", "%20")
				+ "&issuer=" + URLEncoder.encode(dfa.getIssuer(), StandardCharsets.UTF_8).replace("+", "%20");
	}
	
	

}
