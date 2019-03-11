package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {

    Arbitro findByCodigo(Long codigo);
}
