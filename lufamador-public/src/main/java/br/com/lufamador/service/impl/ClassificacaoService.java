package br.com.lufamador.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.model.Intervencao;
import br.com.lufamador.repository.ClassificacaoRepository;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository repository;

    private List<Classificacao> getClassificacoes(String categoria, String chave, String fase) {

        if (fase.equals("2")) {
            return this.repository.listaClassificacoPorCriterioFase02(categoria, chave, fase);
        }
        return this.repository.listaClassificacoPorCriterio(categoria, chave, fase);
    }

    public List<Classificacao> loadClassificacaoPorCategoriaChave(String categoria, String chave, String fase) {
        List<Classificacao> classificacoes = this.getClassificacoes(categoria, chave, fase);
        classificacoes.forEach(classificacao -> {

            List<Intervencao> intervencoes = classificacao.getIntervencoes();
            if (null != classificacoes && !intervencoes.isEmpty()) {

                Intervencao intervencao = intervencoes.stream().reduce((o1, o2) -> o2).orElse(null);
                classificacao.setLast(intervencao);

            }
        });

        return classificacoes;
    }

}
