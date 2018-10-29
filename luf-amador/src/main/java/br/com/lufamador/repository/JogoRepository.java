package br.com.lufamador.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.lufamador.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    @Query(value = "SELECT * FROM luf_jogo WHERE categoria = :categoria AND partida_encerrada = FALSE ORDER BY data_atualizacao  DESC;", nativeQuery = true)
    List<Jogo> getJogosParaTempoReal(@Param(value = "categoria") String categoria);

    @Query(value = "SELECT DISTINCT to_char(luf_jogo.data_partida, 'dd-MM-yyyy') AS dta FROM luf_jogo;", nativeQuery = true)
    List<String> getDatasPartidas();

    @Query(value = "SELECT * FROM luf_jogo WHERE partida_encerrada = TRUE " +
            "AND data_partida BETWEEN ?1 " +
            "AND ?2  AND chave = ?3 AND fase = ?4 ORDER BY data_atualizacao  DESC;", nativeQuery = true)
    List<Jogo> getJogosEditList(LocalDate perInicio, LocalDate perFim, String chave, String fase);

    Jogo findByKeyConfronto(String keyConfronto);

    Jogo findByCodigo(Long codigo);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM luf_tabela_jogos_jogos WHERE jogos_codigo = ?1", nativeQuery = true)
    void deleteFromLufTabelaJogos(Long codigo);

    @Query(value = "SELECT jogo.*\n" +
            "\n" +
            "         FROM luf_jogo jogo\n" +
            "         WHERE 1 = 1\n" +
            "               AND jogo.partida_encerrada = TRUE\n" +
            "               AND jogo.codigo_competicao = 2\n" +
            "               AND jogo.chave = 'A'\n" +
            "               AND jogo.fase = '1'\n" +
            "\n" +
            "         UNION ALL\n" +
            "\n" +
            "         SELECT\n" +
            "\n" +
            "           jogo.*\n" +
            "\n" +
            "         FROM luf_jogo jogo\n" +
            "         WHERE 1 = 1\n" +
            "\n" +
            "               AND jogo.partida_encerrada = TRUE\n" +
            "               AND jogo.codigo_competicao = 2\n" +
            "               AND jogo.chave = 'A'\n" +
            "               AND jogo.fase = '1'", nativeQuery = true)
    List<Jogo> loadJogosClassificacao(@Param(value = "codigo") Integer codigo, @Param(value = "chave") String chave,
            @Param(value = "fase") String fase);

    @Query(value = "SELECT res.*\n" +
            "FROM (\n" +
            "  SELECT *\n" +
            "  FROM luf_jogo\n" +
            "  WHERE codigo_competicao = :codigoCampeonato\n" +
            "        AND agremiacaoa_codigo = :codigoAgremiacao\n" +
            "  UNION\n" +
            "  SELECT *\n" +
            "  FROM luf_jogo\n" +
            "  WHERE codigo_competicao = :codigoCampeonato\n" +
            "        AND agremiacaob_codigo = :codigoAgremiacao\n" +
            ") AS res ORDER BY res.codigo DESC", nativeQuery = true)
    List<Jogo> findByAgremiacaoAndCampeonato(@Param(value = "codigoAgremiacao") Long codigoAgremiacao,
            @Param(value = "codigoCampeonato") Long codigoCampeonato);
}
