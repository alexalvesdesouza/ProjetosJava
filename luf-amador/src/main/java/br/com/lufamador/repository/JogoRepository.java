package br.com.lufamador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    @Query(value = "select * from luf_jogo WHERE data_partida = ?1 order by data_atualizacao  desc;", nativeQuery = true)
    List<Jogo> getJogosParaTempoReal(LocalDate localDate);

    @Query(value = "select DISTINCT to_char(luf_jogo.data_partida, 'dd-MM-yyyy') as dta from luf_jogo;", nativeQuery = true)
    List<String> getDatasPartidas();

    @Query(value = "select * from luf_jogo WHERE partida_encerrada = true " +
            "and data_partida BETWEEN ?1 " +
            "and ?2  and chave = ?3 order by data_atualizacao  desc;", nativeQuery = true)
    List<Jogo> getJogosEditList(LocalDate perInicio, LocalDate perFim, String chave);

}
