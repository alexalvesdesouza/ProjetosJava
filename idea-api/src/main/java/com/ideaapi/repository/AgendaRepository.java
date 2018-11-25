package com.ideaapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ideaapi.model.Agenda;
import com.ideaapi.repository.agenda.AgendaRepositoryQuery;


public interface AgendaRepository extends JpaRepository<Agenda, Long>, AgendaRepositoryQuery {

}
