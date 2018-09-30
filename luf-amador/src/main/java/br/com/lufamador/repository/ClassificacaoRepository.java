package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Classificacao;

public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    @Query(value = "SELECT *\n" +
            "FROM luf_classificacao lc\n" +
            "WHERE lc.categoria = :categoria AND lc.chave = :chave \n" +
            "ORDER BY lc.chave ASC,\n" +
            "  lc.qtd_pontos DESC, lc.qtd_vitorias DESC, lc.gols_pro DESC, lc.gols_contra ASC", nativeQuery = true)
    List<Classificacao> listaClassificacoPorCriterio(@Param(value = "categoria") String categoria,
            @Param(value = "chave") String chave);

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

    @Query(value = "SELECT * FROM luf_classificacao WHERE keymd5 IS NULL", nativeQuery = true)
    List<Classificacao> buscaTodasClassificacoesSemKey();
}
