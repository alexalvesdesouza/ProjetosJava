package br.com.lufamador.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.model.ClassificacaoHistory;
import br.com.lufamador.repository.ClassificacaoHistoryRepository;

@Service
public class ClassificacaoHistoryService {

    private final Logger logger = LoggerFactory.getLogger(ClassificacaoHistoryService.class);

    @Autowired
    private ClassificacaoHistoryRepository repository;

    public ClassificacaoHistory insereHistorico(Classificacao classificacao) {
        return this.repository.saveAndFlush(this.depara(classificacao));
    }

    private ClassificacaoHistory depara(Classificacao classificacao) {

        ClassificacaoHistory history = new ClassificacaoHistory();
        history.setAgremiacao(classificacao.getAgremiacao());
        history.setCampeonatoCodigo(classificacao.getCampeonatoCodigo());
        history.setCategoria(classificacao.getCategoria());
        history.setChave(classificacao.getChave());
        history.setClassificada(classificacao.getClassificada());
        history.setFase(classificacao.getFase());
        history.setGolsContra(classificacao.getGolsContra());
        history.setGolsPro(classificacao.getGolsPro());
        history.setLast(classificacao.getLast());
        history.setQtdVitorias(classificacao.getQtdVitorias());
        history.setQtdPontos(classificacao.getQtdPontos());
        history.setQtdJogos(classificacao.getQtdJogos());
        history.setQtdEmpates(classificacao.getQtdEmpates());
        history.setPosClassificacao(classificacao.getPosClassificacao());

        return history;
    }
}
