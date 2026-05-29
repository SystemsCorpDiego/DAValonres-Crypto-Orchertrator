package com.davalores.crypto.orchestrator.infra.adapter.out.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.davalores.crypto.orchestrator.infra.adapter.out.entity.UsuarioEntity;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Integer> {

	
	@Override
	@Query("SELECT u FROM UsuarioEntity u LEFT JOIN FETCH u.perfiles WHERE u.id = :id")
    Optional<UsuarioEntity> findById(Integer id);
	
	Optional<UsuarioEntity> findByUsuario(String usuario);	

}
