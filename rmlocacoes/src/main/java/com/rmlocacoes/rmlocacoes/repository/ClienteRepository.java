package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
  Cliente findByCodigo(Long codigo);
}
