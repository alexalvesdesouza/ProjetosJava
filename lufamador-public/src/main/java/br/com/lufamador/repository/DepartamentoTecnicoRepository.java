package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.DepartamentoTecnico;

public interface DepartamentoTecnicoRepository extends JpaRepository<DepartamentoTecnico, Long> {

    DepartamentoTecnico findByNumeroAndCategoria(final String numero, final String categoria);

    List<DepartamentoTecnico> findByCategoria(String categoria);

    DepartamentoTecnico findByCodigo(Long codigo);
}
