package com.davalores.crypto.orchestrator.infra.adapter.out.entity;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

	Usuario run(UsuarioEntity usuario);

	UsuarioEntity run(Usuario usuario);

}
