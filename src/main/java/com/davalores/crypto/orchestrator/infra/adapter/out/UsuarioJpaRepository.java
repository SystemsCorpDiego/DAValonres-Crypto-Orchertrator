package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davalores.crypto.orchestrator.domain.model.Usuario;
import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntity;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Integer> {

	Optional<UsuarioEntity> findByUsuario(String usuario);	

}
