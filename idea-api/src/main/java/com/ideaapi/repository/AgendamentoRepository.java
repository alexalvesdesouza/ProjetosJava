package com.ideaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideaapi.model.Agendamento;


public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

}
