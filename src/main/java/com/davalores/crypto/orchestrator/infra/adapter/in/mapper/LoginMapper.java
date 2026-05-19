package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.OauthTokenResponseDto;

@Mapper(componentModel = "spring")
public interface LoginMapper {

	OauthTokenResponseDto run(JWTokenBo dto);	
	
}
