package br.com.lufamador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.lufamador.model.Campeonato;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from luf_campeonato_inscricoes where inscricoes_codigo = ?1", nativeQuery = true)
    void deleteFromLufCampeonatoInscricoes(Long codigo);

    @Modifying
    @Transactional
    @Query(value = "delete from luf_inscricao where codigo = ?1", nativeQuery = true)
    void deleteFromLufInscricao(Long codigo);

}
