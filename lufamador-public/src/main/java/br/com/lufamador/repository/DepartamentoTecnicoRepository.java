package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.DepartamentoTecnico;

public interface DepartamentoTecnicoRepository extends JpaRepository<DepartamentoTecnico, Long> {

    DepartamentoTecnico findByNumeroAndCategoria(final String numero, final String categoria);

    List<DepartamentoTecnico> findByCategoriaAndSubCategoriaOrderByNumeroAsc(String categoria, String subCategoria);

    DepartamentoTecnico findByCodigo(Long codigo);
}
