package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.galeria.Foto;

public interface FotoService {

    Page<Foto> findAll(int page, int count);

    Foto createOrUpdate(Foto entity);

    Foto findByCodigo(Long codigo);

    void delete(Long codigo);

}
