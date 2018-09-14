package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Comunicado;

public interface ComunicadoRepository extends JpaRepository<Comunicado, Long> {


    Comunicado findByCodigo(Long codigo);
}
