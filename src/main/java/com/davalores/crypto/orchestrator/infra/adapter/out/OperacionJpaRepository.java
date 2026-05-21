package com.davalores.crypto.orchestrator.infra.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import com.davalores.crypto.orchestrator.infra.adapter.out.entity.OperacionEntity;

public interface OperacionJpaRepository extends JpaRepository<OperacionEntity, Long> {

}
