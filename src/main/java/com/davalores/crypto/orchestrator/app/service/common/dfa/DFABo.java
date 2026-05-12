package com.davalores.crypto.orchestrator.app.service.common.dfa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DFABo {
	private String account;

	private String issuer;

	private String sharedSecret;

	private String authenticatorBarCode;

	@Override
	public String toString() {
		return "DFABo{" + "account='" + account + '\'' + ", issuer='" + issuer + '\'' + ", sharedSecret=*******}";
	}

	public static class Builder {
		String account;
		String issuer;
		String sharedSecret;
		String authenticatorBarCode;

		public Builder() {
		}

		public Builder(DFABo bean) {
			this.account = bean.account;
			this.issuer = bean.issuer;
			this.sharedSecret = bean.sharedSecret;
			this.authenticatorBarCode = bean.authenticatorBarCode;
		}

		public Builder account(String accountParam) {
			this.account = accountParam;
			return this;
		}

		public Builder issuer(String issuerParam) {
			this.issuer = issuerParam;
			return this;
		}

		public Builder sharedSecret(String sharedSecretParam) {
			this.sharedSecret = sharedSecretParam;
			return this;
		}

		public Builder authenticatorBarCode(String authenticatorBarCodeParam) {
			this.authenticatorBarCode = authenticatorBarCodeParam;
			return this;
		}

		public DFABo build() {
			return new DFABo(this);
		}
	}

	DFABo(Builder builder) {
		this.account = builder.account;
		this.issuer = builder.issuer;
		this.sharedSecret = builder.sharedSecret;
		this.authenticatorBarCode = builder.authenticatorBarCode;
	}

}
