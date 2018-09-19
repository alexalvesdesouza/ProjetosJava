package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.galeria.Video;

public interface VideoService {

    Page<Video> findAll(int page, int count);

    Video createOrUpdate(Video entity);

    Video findByCodigo(Long codigo);

    void delete(Long codigo);

}
