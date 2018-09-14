package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Edital;

public interface EditalRepository extends JpaRepository<Edital, Long> {

    Edital findByCodigo(Long codigo);
}
