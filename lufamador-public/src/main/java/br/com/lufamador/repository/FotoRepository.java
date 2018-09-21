package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Foto;

public interface FotoRepository extends JpaRepository<Foto, Long> {

    Foto findByCodigo(Long codigo);
}
