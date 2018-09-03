package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.model.AtletaHistory;

public interface AtletaHistoryRepository extends JpaRepository<AtletaHistory, Long> {

    AtletaHistory findByCodigo(Long codigo);

    List<AtletaHistory> findByAtleta(Atleta atleta);
}
