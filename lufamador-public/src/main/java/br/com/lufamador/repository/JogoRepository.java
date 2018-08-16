package br.com.lufamador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    @Query(value = "select * from luf_jogo WHERE data_partida = ?1 order by data_atualizacao  desc;", nativeQuery = true)
    List<Jogo> getJogosParaTempoReal(LocalDate localDate);

}
