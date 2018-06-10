package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.Agremiacao;

public interface AgremiacaoRepository extends JpaRepository<Agremiacao, Long> {

    Agremiacao findByNomeSigla(String nome);


    @Query(value = "select lag.*\n" +
            "from luf_agremiacao lag\n" +
            "where lag.codigo not in (select inscricao.agremiacao_codigo\n" +
            "    from luf_inscricao inscricao inner join luf_campeonato_inscricoes cainc on inscricao.codigo = cainc.inscricoes_codigo " +
            "where cainc.campeonato_codigo = ?1)\n" +
            "union\n" +
            "select lag.*\n" +
            "from luf_agremiacao lag left join luf_inscricao lin on lin.agremiacao_codigo = lag.codigo\n" +
            "where lin.codigo  is null", nativeQuery = true)
    List<Agremiacao> getAgremiacoesDisponiveis(Long codigoCampeonato);


    @Query(value = "select lag.*\n" +
            "from luf_agremiacao lag left join luf_inscricao lin on lin.agremiacao_codigo = lag.codigo\n" +
            "where lin.codigo  in ( select lci.inscricoes_codigo from luf_campeonato_inscricoes lci where lci.campeonato_codigo = ?1 )\n", nativeQuery = true)
    List<Agremiacao> getAgremiacoesInscritas(Long codigoCampeonato);

     @Query(value = "select count(1) as tem_inscricao from luf_inscricao where agremiacao_codigo = ?1", nativeQuery = true)
    int localizaInscricaoDeAgremiacao(Long codigo);

}
