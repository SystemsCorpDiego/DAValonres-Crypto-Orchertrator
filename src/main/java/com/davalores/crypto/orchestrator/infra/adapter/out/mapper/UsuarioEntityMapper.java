package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

	Usuario run(UsuarioEntity usuario);

	UsuarioEntity run(Usuario usuario);

}
