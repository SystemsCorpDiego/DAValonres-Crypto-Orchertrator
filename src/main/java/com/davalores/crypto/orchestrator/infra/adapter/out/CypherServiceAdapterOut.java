package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CypherServiceAdapterOut implements CypherServicePortOut {

	private static final String SALT = "salt";
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

	private SecretKey secretKey;
	private IvParameterSpec iv;

	public CypherServiceAdapterOut(@Value("${auth.2fa.password:}") String password) {
		this.secretKey = this.generateEncryptionKey(password, SALT);
		this.iv = this.generateIv();
	}

	@Override
	public String encrypt(String input) {
		byte[] cypherText = new byte[0];
		try {
			Cipher cypher = Cipher.getInstance(ALGORITHM);
			cypher.init(Cipher.ENCRYPT_MODE, this.secretKey, this.iv);
			cypherText = cypher.doFinal(input.getBytes());
		} catch (Exception e)  {
			log.error(e.getMessage());
		}
		return Base64.getEncoder()
				.encodeToString(cypherText);
	}

	@Override
	public String decrypt(String cypherText) {
		byte[] plainText = new byte[0];
		try {
			Cipher cypher = Cipher.getInstance(ALGORITHM);
			cypher.init(Cipher.DECRYPT_MODE, this.secretKey, this.iv);
			plainText = cypher.doFinal(Base64.getDecoder()
					.decode(cypherText));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return new String(plainText);
	}

	private SecretKey generateEncryptionKey(String password, String salt) {
		SecretKey secret = null;
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65353, 256);
			secret = new SecretKeySpec(factory.generateSecret(spec)
					.getEncoded(), "AES");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.error(e.getMessage());
		}
		return secret;
	}

	private IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		for (int i = 0; i < 16; i++) {
			iv[i] = (byte) i;
		}
		return new IvParameterSpec(iv);
	}

}
