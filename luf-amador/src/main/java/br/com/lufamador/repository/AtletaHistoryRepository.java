package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AtletaHistoryRepository extends JpaRepository<AtletaHistory, Long> {

    AtletaHistory findByCodigo(Long codigo);

    List<AtletaHistory> findByAtleta(Atleta atleta);
}
