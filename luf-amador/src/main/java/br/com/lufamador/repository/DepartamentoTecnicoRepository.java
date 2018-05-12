package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.model.Local;

public interface DepartamentoTecnicoRepository extends JpaRepository<DepartamentoTecnico, Long> {

    DepartamentoTecnico findByNumeroAndAndCategoria(final String numero, final String categoria);
}
