package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.NotaOficial;

public interface NotaOficialRepository extends JpaRepository<NotaOficial, Long> {

    NotaOficial findByCodigo(Long codigo);
}
