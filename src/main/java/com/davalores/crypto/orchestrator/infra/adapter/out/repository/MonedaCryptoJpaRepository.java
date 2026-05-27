package com.davalores.crypto.orchestrator.infra.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davalores.crypto.orchestrator.infra.adapter.out.entity.MonedaCryptoEntity;

public interface MonedaCryptoJpaRepository extends JpaRepository<MonedaCryptoEntity, Integer> {
	
	
}
