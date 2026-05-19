package com.davalores.crypto.orchestrator.app.service.common.dfa;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;

import de.taimos.totp.TOTP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidarDFACodeImpl implements ValidarDFACode {

	//private final UsuarioAuthenticationStorage usuarioAuthenticationStorage;
	private final CypherServicePortOut cypher;
	private final MessageSource messageSource;
	
	//opSecret: semilla del usuario
	public boolean run(String seed,  String dfaCode) {
		log.debug("Input parameter -> dfaCode {}", dfaCode);
		/*
		  	Integer userId = UsuarioInfo.getCurrentAuditor();
			Optional<String> opSecret = usuarioAuthenticationStorage.getTwoFactorAuthenticationSecret(userId);
			if (opSecret == null) {
				String errorMsg = messageSource.getMessage(DFAExceptionEnum.DFA_CODE_NULL.getMsgKey(), null, new Locale("es"));	
				throw new BusinessException(DFAExceptionEnum.DFA_CODE_NULL.name(), errorMsg);
			}
		 */
		 
		String decryptedSecret = cypher.desencriptar(seed);
		if ( decryptedSecret.equals(""))
			return false;
		
		String totpCode = getTotpCode(decryptedSecret);
		return totpCode.equals(dfaCode);
	}

	private String getTotpCode(String secretKey) {
		Base32 base32 = new Base32();
		byte[] bytes = base32.decode(secretKey);
		String hexKey = Hex.encodeHexString(bytes);
		return TOTP.getOTP(hexKey);
	}

}
