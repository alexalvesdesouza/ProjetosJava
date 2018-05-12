package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
  Funcionario findByCodigo(Long codigo);
}
