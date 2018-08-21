package br.com.lufamador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    @Query(value = "select * from luf_jogo WHERE data_partida BETWEEN ?1 " +
            "and ?2 order by data_atualizacao  desc;", nativeQuery = true)
    List<Jogo> getJogosParaTempoReal(LocalDate perInicio, LocalDate perFim);

    @Query(value = "select DISTINCT to_char(luf_jogo.data_partida, 'dd-MM-yyyy') as dta from luf_jogo;\n", nativeQuery = true)
    List<String> getDatasPartidas();

}
