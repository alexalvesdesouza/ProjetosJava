package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Receber;

public interface ReceberRepository extends JpaRepository<Receber, Long> {
  Receber findByCodigo(Long codigo);
}
