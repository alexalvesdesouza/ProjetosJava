package br.com.lufamador.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Arbitro;

public interface ArbitroService {

    Page<Arbitro> findAll(int page, int count);

    List<Arbitro> getArbitros();

    Arbitro createOrUpdate(Arbitro arbitro);

    Arbitro findByCodigo(Long id);

    void delete(Long id);

}
