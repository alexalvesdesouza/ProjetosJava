package br.com.lufamador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.lufamador.model.Classificacao;

public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long> {

    @Query(value = "select * from luf_classificacao lc order by lc.chave asc, lc.qtd_pontos desc, lc.qtd_vitorias desc, lc.gols_pro desc, lc.gols_contra asc;", nativeQuery = true)
    List<Classificacao> listaClassificacoPorCriterio();


    @Query(value = "select * from luf_classificacao where agremiacao_codigo = ?1", nativeQuery = true)
    Classificacao findByAgremiacao_Codigo(final Long codigoAgremiacao);
}
