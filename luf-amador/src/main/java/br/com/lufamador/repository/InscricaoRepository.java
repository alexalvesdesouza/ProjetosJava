package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.model.Inscricao;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

}
