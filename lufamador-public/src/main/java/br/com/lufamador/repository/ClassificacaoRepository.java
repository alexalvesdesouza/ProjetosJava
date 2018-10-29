package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.lufamador.model.Classificacao;


public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    @Query(value = "select *\n" +
            "from luf_classificacao lc\n" +
            "where lc.fase = :fase and lc.categoria = :categoria and lc.chave = :chave \n" +
            "order by lc.pos_classificacao asc;", nativeQuery = true)
    List<Classificacao> listaClassificacoPorCriterio(@Param(value = "categoria") String categoria,
            @Param(value = "chave") String chave, @Param(value = "fase") String fase);

//
//    @Query(value = "select res.codigo, res.chave, res.gols_contra, res.gols_pro, res.keymd5, res.pos_classificacao, res.qtd_jogos, res.qtd_vitorias, res.qtd_pontos,\n" +
//            "res.qtd_vitorias, res.agremiacao_codigo, res.campeonato_codigo, res.categoria, res.classificada, res.fase, res.qtd_derrotas, res.qtd_empates\n" +
//            "from (\n" +
//            "  SELECT\n" +
//            "    lc.*,\n" +
//            "    jogo.codigo as jogo\n" +
//            "  FROM luf_classificacao lc INNER JOIN luf_jogo jogo\n" +
//            "      ON jogo.fase = lc.fase AND jogo.fase = :fase AND jogo.agremiacaoa_codigo = lc.agremiacao_codigo\n" +
//            "\n" +
//            "  WHERE lc.fase = :fase AND lc.categoria = :categoria AND lc.chave = :chave\n" +
//            "\n" +
//            "  UNION ALL\n" +
//            "\n" +
//            "  SELECT\n" +
//            "    lc.*,\n" +
//            "    jogo.codigo as jogo\n" +
//            "\n" +
//            "  FROM luf_classificacao lc INNER JOIN luf_jogo jogo\n" +
//            "      ON jogo.fase = lc.fase AND jogo.fase = :fase AND jogo.agremiacaob_codigo = lc.agremiacao_codigo\n" +
//            "\n" +
//            "  WHERE lc.fase = :fase AND lc.categoria = :categoria AND lc.chave = :chave\n" +
//            "\n" +
//            ") as res order by res.jogo asc, res.pos_classificacao asc;", nativeQuery = true)
//    List<Classificacao> listaClassificacoPorCriterioFase02(@Param(value = "categoria") String categoria,
//            @Param(value = "chave") String chave, @Param(value = "fase") String fase);

    @Query(value = "select * from luf_classificacao WHERE  categoria = :categoria" +
            " AND fase = :fase and chave = :chave ORDER BY pos_classificacao asc;", nativeQuery = true)
    List<Classificacao> listaClassificacoPorCriterioFase02(@Param(value = "categoria") String categoria,
            @Param(value = "chave") String chave, @Param(value = "fase") String fase);


    @Query(value = "select * from luf_classificacao where agremiacao_codigo = ?1", nativeQuery = true)
    Classificacao findByAgremiacao_Codigo(final Long codigoAgremiacao);
}
