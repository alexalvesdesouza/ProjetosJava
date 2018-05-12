package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Atleta;

public interface AgremiacaoRepository extends JpaRepository<Agremiacao, Long> {

    Agremiacao findByNomeSigla(String nome);

}
