package com.davalores.crypto.orchestrator.infra.adapter.out.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface UsuarioEntityMapper {

	List<Usuario> run(List<UsuarioEntity> usuario);
	
	Usuario run(UsuarioEntity usuario);

	UsuarioEntity run(Usuario usuario);

}
