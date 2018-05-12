package com.rmlocacoes.rmlocacoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rmlocacoes.rmlocacoes.model.Convidado;
import com.rmlocacoes.rmlocacoes.model.Evento;

public interface ConvidadoRepository extends JpaRepository<Convidado, String> {

	Iterable<Convidado> findByEvento(Evento evento);

}
