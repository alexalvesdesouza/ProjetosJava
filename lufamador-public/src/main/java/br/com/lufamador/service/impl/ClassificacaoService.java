package br.com.lufamador.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.repository.ClassificacaoRepository;

@Service
public class ClassificacaoService {

    @Autowired
    private ClassificacaoRepository repository;

    private List<Classificacao> getClassificacoes(String categoria) {
        List<Classificacao> classificacoes = this.repository.listaClassificacoPorCriterio()
                .stream().filter(item -> item.getCategoria().equals(categoria)).collect(Collectors.toList());

        int posicao = 1;
        String chave = "A";
        for (Classificacao classificacao : classificacoes) {

            if (!chave.equals(classificacao.getChave())) {
                chave = classificacao.getChave();
                posicao = 1;
            }
            classificacao.setPosClassificacao(posicao);
            this.registraPosicaoTabelaClassificacao(classificacao);
            posicao++;
        }
        return classificacoes;
    }

    public List<Classificacao> loadClassificacaoPorCategoriaChave(String categoria, String chave) {
        return this.getClassificacoes(categoria).stream()
                .filter(jg -> jg.getChave().equals(chave))
                .collect(Collectors.toList());
    }

    private void registraPosicaoTabelaClassificacao(Classificacao classificacao) {
        this.repository.saveAndFlush(classificacao);
    }

}
