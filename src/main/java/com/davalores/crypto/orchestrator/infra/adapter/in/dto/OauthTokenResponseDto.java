package com.davalores.crypto.orchestrator.infra.adapter.in.dto;

import lombok.Data;

@Data
public class OauthTokenResponseDto {

	private String token;
	private String tokenRefresco;

}
