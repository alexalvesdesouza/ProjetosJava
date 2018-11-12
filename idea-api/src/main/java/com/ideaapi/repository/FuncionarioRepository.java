package com.ideaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideaapi.model.Funcionario;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
