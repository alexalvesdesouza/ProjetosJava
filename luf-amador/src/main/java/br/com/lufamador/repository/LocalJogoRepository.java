package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.LocalJogo;

public interface LocalJogoRepository extends JpaRepository<LocalJogo, Long> {

}
