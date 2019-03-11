package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TjduRepository extends JpaRepository<Tjdu, Long> {

    Tjdu findByNumeroAndCategoria(final String numero, final String categoria);

    List<Tjdu> findByCategoria(String categoria);

    Tjdu findByCodigo(Long codigo);
}
