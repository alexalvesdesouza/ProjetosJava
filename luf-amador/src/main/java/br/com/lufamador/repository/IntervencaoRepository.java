package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Intervencao;

public interface IntervencaoRepository extends JpaRepository<Intervencao, Long> {

}
