package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Comunicado;
import br.com.lufamador.model.Tjdu;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {

    Tjdu findByNumeroAndCategoria(final String numero, final String categoria);

    List<Tjdu> findByCategoria(String categoria);

    Tjdu findByCodigo(Long codigo);
}
