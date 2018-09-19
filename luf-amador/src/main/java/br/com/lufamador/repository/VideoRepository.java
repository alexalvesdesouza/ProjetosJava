package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.galeria.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findByCodigo(Long codigo);
}
