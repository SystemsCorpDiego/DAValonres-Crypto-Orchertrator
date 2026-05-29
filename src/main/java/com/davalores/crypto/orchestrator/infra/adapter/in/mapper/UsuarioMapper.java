package com.davalores.crypto.orchestrator.infra.adapter.in.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.CrearUsuarioDto;
import com.davalores.crypto.orchestrator.infra.adapter.in.dto.UsuarioDto;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	public Usuario run(CrearUsuarioDto dto);
	
	public List<UsuarioDto> run(List<Usuario> usuario);
	public UsuarioDto run(Usuario usuario);
}
