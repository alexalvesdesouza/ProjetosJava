package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Nota;

public interface NotaService {


    Page<Nota> findAll(int page, int count);

    Nota createOrUpdate(Nota entity);

    Nota findByCodigo(Long codigo);

    Nota findByNumero(String numero);

    void delete(Long codigo);
}
