package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    @Query(value = "SELECT *\n" +
            "FROM luf_classificacao lc\n" +
            "WHERE lc.categoria = :categoria AND lc.chave = :chave AND lc.fase = :fase \n" +
            "ORDER BY lc.chave ASC,\n" +
            "  lc.qtd_pontos DESC, lc.qtd_vitorias DESC, lc.gols_pro DESC, lc.gols_contra ASC", nativeQuery = true)
    List<Classificacao> listaClassificacoPorCriterio(@Param(value = "categoria") String categoria,
            @Param(value = "chave") String chave, @Param(value = "fase") String fase);

    @Query(value = "SELECT *\n" +
            "FROM luf_classificacao lc\n" +
            "WHERE lc.fase = :fase AND agremiacao_codigo IN (:codAgremiacaoA, :codAgremiacaoB) AND lc.categoria = :categoria AND lc.chave = :chave \n" +
            "ORDER BY lc.chave ASC,\n" +
            "lc.gols_pro DESC", nativeQuery = true)
    List<Classificacao> listaClassificacoPorCriterioFase2(@Param(value = "categoria") String categoria,
            @Param(value = "chave") String chave, @Param(value = "fase") String fase,
            @Param(value = "codAgremiacaoA") Long codAgremiacaoA, @Param(value = "codAgremiacaoB") Long codAgremiacaoB


    );

    Classificacao findByAgremiacaoAndKeyMD5(Agremiacao agremiacao, String key);

    Classificacao findByKeyMD5AndCampeonatoCodigo(String key, Integer campeonatoCodigo);

    Classificacao findByAgremiacaoAndCategoriaAndFaseAndChave(Agremiacao agremiacao, String categoria, String fase,
            String chave);

    @Query(value = "SELECT * FROM luf_classificacao WHERE keymd5 IS NULL", nativeQuery = true)
    List<Classificacao> buscaTodasClassificacoesSemKey();

    List<Classificacao> findByCampeonatoCodigoAndChaveAndFase(Integer codigo, String chave, String fase);


    @Query(value = "SELECT res.agremiacao_codigo\n" +
            "FROM (\n" +
            "  SELECT\n" +
            "    count(1) AS total,\n" +
            "    cls.agremiacao_codigo\n" +
            "  FROM luf_classificacao cls\n" +
            "\n" +
            "  WHERE 1 = 1\n" +
            "        AND cls.fase = :fase\n" +
            "        AND cls.chave = :chave\n" +
            "        AND cls.categoria = :categoria\n" +
            "  GROUP BY cls.agremiacao_codigo\n" +
            ") AS res WHERE res.total > 1", nativeQuery = true)
    Integer loadClassificacoesDuplicadas(@Param(value = "fase") String fase, @Param(value = "chave") String chave,
            @Param(value = "categoria") String categoria);
}
