package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.lufamador.model.Agremiacao;

public interface AgremiacaoRepository extends JpaRepository<Agremiacao, Long> {

    Agremiacao findByNomeSigla(String nome);

    Agremiacao findByNomeAndCategoria(String nome, String categoria);

    Agremiacao findByCodigo(Long codigo);

    @Query(value = "SELECT * FROM luf_agremiacao agr\n" +
            "WHERE agr.codigo NOT IN\n" +
            "\n" +
            "\t\t\t(SELECT inscricoes_codigo FROM luf_campeonato_inscricoes\n" +
            "\t\t\t\tWHERE campeonato_codigo = ?1) AND agr.categoria = ?2", nativeQuery = true)
    List<Agremiacao> getAgremiacoesDisponiveis(Long codigoCampeonato, String categoria);


    @Query(value = "SELECT lag.*\n" +
            "FROM luf_agremiacao lag LEFT JOIN luf_inscricao lin ON lin.agremiacao_codigo = lag.codigo\n" +
            "WHERE lin.codigo  IN ( SELECT lci.inscricoes_codigo FROM luf_campeonato_inscricoes lci WHERE lci.campeonato_codigo = ?1 )\n", nativeQuery = true)
    List<Agremiacao> getAgremiacoesInscritas(Long codigoCampeonato);

    @Query(value = "SELECT count(1) AS tem_inscricao FROM luf_inscricao WHERE agremiacao_codigo = ?1", nativeQuery = true)
    int localizaInscricaoDeAgremiacao(Long codigo);

    @Query(value = "SELECT * FROM luf_agremiacao WHERE codigo IN (SELECT agremiacaob_codigo FROM luf_jogo WHERE partida_encerrada = FALSE AND codigo_competicao = :codigo)\n" +
            "  UNION\n" +
            "SELECT * FROM luf_agremiacao WHERE codigo IN (SELECT agremiacaoa_codigo FROM luf_jogo WHERE partida_encerrada = FALSE AND codigo_competicao = :codigo)",
            nativeQuery = true)
    List<Agremiacao> getAgremiacoesEmJogo(@Param(value = "codigo") Long codigo);

    @Query(value = "SELECT *\n" +
            "FROM luf_agremiacao agr INNER JOIN luf_classificacao cla ON cla.agremiacao_codigo = agr.codigo\n" +
            "WHERE cla.campeonato_codigo = :codigo\n" +
            "AND cla.classificada = TRUE\n" +
            "AND cla.fase = :fase", nativeQuery = true)
    List<Agremiacao> getAgremiacoesClassificadas(@Param(value = "codigo") Long codigo, @Param(value = "fase") String fase);
}
