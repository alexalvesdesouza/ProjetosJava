package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Pagar;

public interface PagarRepository extends JpaRepository<Pagar, Long> {
  Pagar findByCodigo(Long codigo);
}
