package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
