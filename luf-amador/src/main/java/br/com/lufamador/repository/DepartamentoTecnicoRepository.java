package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoTecnicoRepository extends JpaRepository<DepartamentoTecnico, Long> {

    DepartamentoTecnico findByNumeroAndCategoria(final String numero, final String categoria);

    List<DepartamentoTecnico> findByCategoria(String categoria);

    DepartamentoTecnico findByCodigo(Long codigo);
}
