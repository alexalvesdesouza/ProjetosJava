package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.repository.ClassificacaoRepository;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository repository;

    private List<Classificacao> getClassificacoes(String categoria, String chave) {
        return this.repository.listaClassificacoPorCriterio(categoria, chave);
    }

    public List<Classificacao> loadClassificacaoPorCategoriaChave(String categoria, String chave) {
        return this.getClassificacoes(categoria, chave);
    }

}
