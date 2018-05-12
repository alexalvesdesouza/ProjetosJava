package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.FolhaPagamento;

public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {
  FolhaPagamento findByCodigo(Long codigo);
}
