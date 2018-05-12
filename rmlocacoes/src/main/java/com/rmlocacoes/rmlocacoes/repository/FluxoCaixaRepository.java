package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.FluxoCaixa;

public interface FluxoCaixaRepository extends JpaRepository<FluxoCaixa, Long> {
  FluxoCaixa findByCodigo(Long codigo);
}
