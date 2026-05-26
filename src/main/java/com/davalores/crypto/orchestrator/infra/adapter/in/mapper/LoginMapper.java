package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.app.service.common.jwt.JWTokenBo;
import com.davalores.crypto.orchestrator.domain.model.TokenOauth;

@Mapper(componentModel = "spring")
public interface LoginMapper {

	TokenOauth run(JWTokenBo dto);	
	
}
