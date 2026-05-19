package com.davalores.crypto.orchestrator.domain.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DfaToken {
	
	private String account;
	private String issuer;
	private String sharedSecret;
	private String qrCodeImg;	
	
	public String getAuthenticatorBarCode() {
		return "otpauth://totp/"
				+ URLEncoder.encode(this.getIssuer() + ":" + this.getAccount(), StandardCharsets.UTF_8).replace("+", "%20")
				+ "?secret=" + URLEncoder.encode(this.getSharedSecret(), StandardCharsets.UTF_8).replace("+", "%20")
				+ "&issuer=" + URLEncoder.encode(this.getIssuer(), StandardCharsets.UTF_8).replace("+", "%20");
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getSharedSecret() {
		return sharedSecret;
	}
	public void setSharedSecret(String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}

	public String getQrCodeImg() {
		return qrCodeImg;
	}

	public void setQrCodeImg(String qrCodeImg) {
		this.qrCodeImg = qrCodeImg;
	}
	
}
