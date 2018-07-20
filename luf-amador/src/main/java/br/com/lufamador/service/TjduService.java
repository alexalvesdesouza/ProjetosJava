package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Tjdu;

public interface TjduService {
    Page<Tjdu> findAll(int page, int count);

    Tjdu createOrUpdate(Tjdu entity);

    Tjdu findByCodigo(Long codigo);

    void delete(Long codigo);
    
}
