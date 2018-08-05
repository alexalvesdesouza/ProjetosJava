package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
