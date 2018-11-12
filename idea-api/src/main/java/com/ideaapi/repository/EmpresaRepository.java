package com.ideaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideaapi.model.Empresa;


public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
