package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Local;

public interface LocalRepository extends JpaRepository<Local, Long> {

    Local findByNome(String nome);
}
