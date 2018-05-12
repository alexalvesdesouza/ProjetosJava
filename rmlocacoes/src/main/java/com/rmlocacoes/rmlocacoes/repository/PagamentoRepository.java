package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
  Pagamento findByCodigo(Long codigo);
}
