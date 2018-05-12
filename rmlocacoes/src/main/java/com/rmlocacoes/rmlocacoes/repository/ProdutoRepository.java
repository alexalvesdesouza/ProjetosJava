package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
  Produto findByCodigo(Long codigo);
}
