package br.com.lufamador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.lufamador.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    @Query(value = "SELECT * FROM luf_jogo WHERE data_partida = ?1 ORDER BY data_atualizacao  DESC;", nativeQuery = true)
    List<Jogo> getJogosParaTempoReal(LocalDate localDate);

    @Query(value = "SELECT DISTINCT to_char(luf_jogo.data_partida, 'dd-MM-yyyy') AS dta FROM luf_jogo;", nativeQuery = true)
    List<String> getDatasPartidas();

    @Query(value = "SELECT * FROM luf_jogo WHERE partida_encerrada = TRUE " +
            "AND data_partida BETWEEN ?1 " +
            "AND ?2  AND chave = ?3 ORDER BY data_atualizacao  DESC;", nativeQuery = true)
    List<Jogo> getJogosEditList(LocalDate perInicio, LocalDate perFim, String chave);

    Jogo findByKeyConfronto(String keyConfronto);

    Jogo findByCodigo(Long codigo);

    @Modifying
    @Transactional
    @Query(value = "delete from luf_tabela_jogos_jogos where jogos_codigo = ?1", nativeQuery = true)
    void deleteFromLufTabelaJogos(Long codigo);

}
