package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Atleta;
import br.com.lufamador.model.AtletaHistory;
import br.com.lufamador.repository.AtletaHistoryRepository;

@Service
public class AtletaHistoryServiceImpl {

    @Autowired
    private AtletaHistoryRepository repository;

    public AtletaHistory insereNovoRegistroNoHistorico(Atleta atleta, Agremiacao agremiacao, String temporada) {
        AtletaHistory history = new AtletaHistory();
        history.setAgremiacao(agremiacao);
        history.setAtleta(atleta);
        history.setTemporada(temporada);

        return repository.saveAndFlush(history);

    }

    public List<AtletaHistory> history(Atleta atleta) {
        return this.repository.findByAtleta(atleta);
    }
}
