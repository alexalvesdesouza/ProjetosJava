package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Campeonato;

public interface CampeonatoService {

    Page<Campeonato> findAll(int page, int count);

    Campeonato createOrUpdate(Campeonato entity);

    Campeonato findByCodigo(Long codigo);

    void delete(Long codigo);
}
