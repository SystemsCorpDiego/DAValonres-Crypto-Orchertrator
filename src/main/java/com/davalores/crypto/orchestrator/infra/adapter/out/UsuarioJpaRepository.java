package com.davalores.crypto.orchestrator.infra.adapter.out;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davalores.crypto.orchestrator.domain.model.Usuario;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByDescripcion(String usuario);	

}
