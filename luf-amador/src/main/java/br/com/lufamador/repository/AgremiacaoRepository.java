package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Jogo;

public interface AgremiacaoRepository extends JpaRepository<Agremiacao, Long> {

    Agremiacao findByNomeSigla(String nome);

    Agremiacao findByNomeAndCategoria(String nome, String categoria);

    Agremiacao findByCodigo(Long codigo);

    @Query(value = "select * from luf_agremiacao agr\n" +
            "where agr.codigo not in\n" +
            "\n" +
            "\t\t\t(select inscricoes_codigo from luf_campeonato_inscricoes\n" +
            "\t\t\t\twhere campeonato_codigo = ?1) and agr.categoria = ?2", nativeQuery = true)
    List<Agremiacao> getAgremiacoesDisponiveis(Long codigoCampeonato, String categoria);


    @Query(value = "select lag.*\n" +
            "from luf_agremiacao lag left join luf_inscricao lin on lin.agremiacao_codigo = lag.codigo\n" +
            "where lin.codigo  in ( select lci.inscricoes_codigo from luf_campeonato_inscricoes lci where lci.campeonato_codigo = ?1 )\n", nativeQuery = true)
    List<Agremiacao> getAgremiacoesInscritas(Long codigoCampeonato);

    @Query(value = "select count(1) as tem_inscricao from luf_inscricao where agremiacao_codigo = ?1", nativeQuery = true)
    int localizaInscricaoDeAgremiacao(Long codigo);

    @Query(value = "SELECT * FROM luf_agremiacao WHERE codigo IN (SELECT agremiacaob_codigo FROM luf_jogo WHERE partida_encerrada = FALSE AND codigo_competicao = :codigo)\n" +
            "  UNION\n" +
            "SELECT * FROM luf_agremiacao WHERE codigo IN (SELECT agremiacaoa_codigo FROM luf_jogo WHERE partida_encerrada = FALSE AND codigo_competicao = :codigo)",
            nativeQuery = true)
    List<Agremiacao> getAgremiacoesEmJogo(@Param(value = "codigo") Long codigo);

}
