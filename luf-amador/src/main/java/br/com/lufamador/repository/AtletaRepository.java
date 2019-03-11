package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    Atleta findByCodigo(Long codigo);

}
