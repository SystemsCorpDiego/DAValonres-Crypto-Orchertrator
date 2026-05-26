package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.davalores.crypto.orchestrator.app.port.out.CypherServicePortOut;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CypherServiceAdapterOut implements CypherServicePortOut {

	private static final String SALT = "salt";
	private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
	//private static final String ALGORITHM = "AES/GCM/NoPadding";
	private static final int TAG_LENGTH_BITS = 128;
	private static final int IV_LENGTH_BYTES = 12;
	private String password;
	
	private SecretKey secretKey;
	private IvParameterSpec iv;

	public CypherServiceAdapterOut(@Value("${auth.2fa.password:}") String password) {
		this.password = password;
		this.secretKey = this.generarEncriptacionKey(password, SALT);
		this.iv = this.generateIv();
	}

	@Override
	public String encriptar(String input) {
		
		if( ALGORITHM.equals("AES/GCM/NoPadding") )
			return encriptarAES_GCM(input);
		
		return encriptarAES_CBC(input);		
	}

	@Override
	public String desencriptar(String cypherText) {

		if( ALGORITHM.equals("AES/GCM/NoPadding") )
			return desencriptarAES_GCM(cypherText);
		
		return desencriptarAES_CBC(cypherText);				
	}

	private String encriptarAES_CBC(String input) {
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
	
	public String desencriptarAES_CBC(String cypherText) {
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
	
	private String  encriptarAES_GCM(String input) {
		byte[] combined = new byte[0];
		try {
		    byte[] keyBytes = this.password.getBytes("UTF-8"); //"UTF-8"
	        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
	        
	        byte[] iv = new byte[IV_LENGTH_BYTES];
	        SecureRandom secureRandom = new SecureRandom();
	        secureRandom.nextBytes(iv); // Always generate a new IV!
	
	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BITS, iv);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
	
	        byte[] cipherText = cipher.doFinal(input.getBytes("UTF-8")); //"UTF-8"
	
	        // Prepend the IV to the ciphertext so it's available for decryption
	        combined = new byte[iv.length + cipherText.length];
	        System.arraycopy(iv, 0, combined, 0, iv.length);
	        System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);
		} catch (Exception e) { 
			log.error(e.getMessage()); 
			log.error(e.toString());
		}
        return Base64.getEncoder().encodeToString(combined);		
	}
	
	public String desencriptarAES_GCM(String cypherText) {
		byte[] plainText = new byte[0];
		String decyphed = "";
		try {
			byte[] keyBytes = this.password.getBytes("UTF-8");
	        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
	        
	        byte[] combined = Base64.getDecoder().decode(cypherText);

	        // Extract the IV and ciphertext from the combined byte array
	        byte[] iv = new byte[IV_LENGTH_BYTES];
	        byte[] cipherText = new byte[combined.length - IV_LENGTH_BYTES];
	        
	        System.arraycopy(combined, 0, iv, 0, iv.length);
	        System.arraycopy(combined, iv.length, cipherText, 0, cipherText.length);

	        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
	        GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BITS, iv);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

	        plainText = cipher.doFinal(cipherText);
	        decyphed = new String(plainText, "UTF-8");
		} catch (Exception e) { 
			log.error(e.getMessage()); 
			log.error(e.toString());
		}

        return decyphed;
	}
	
	private SecretKey generarEncriptacionKey(String password, String salt) {
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
