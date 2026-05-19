package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearUsuarioDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.UsuarioDto;

@Mapper
public interface UsuarioMapper {

	public Usuario run(CrearUsuarioDto dto);
	
	public UsuarioDto run(Usuario usuario);
}
