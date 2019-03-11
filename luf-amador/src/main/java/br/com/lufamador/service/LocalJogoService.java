package br.com.lufamador.service;

import org.springframework.data.domain.Page;

public interface LocalJogoService {

    Page<LocalJogo> findAll(int page, int count);

    LocalJogo createOrUpdate(LocalJogo agremiacao);

    LocalJogo findByCodigo(Long id);

    void delete(Long id);

}
