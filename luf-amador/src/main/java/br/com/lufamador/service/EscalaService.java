package br.com.lufamador.service;

import org.springframework.data.domain.Page;

public interface EscalaService {

    Page<EscalaArbitros> findAll(int page, int count);

    EscalaArbitros createOrUpdate(EscalaArbitros entity);

    EscalaArbitros findByCodigo(Long codigo);

    void delete(Long codigo);
}
