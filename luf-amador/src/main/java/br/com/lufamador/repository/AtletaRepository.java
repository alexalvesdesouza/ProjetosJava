package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Atleta;

public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    Atleta findByCodigo(Long codigo);

}
