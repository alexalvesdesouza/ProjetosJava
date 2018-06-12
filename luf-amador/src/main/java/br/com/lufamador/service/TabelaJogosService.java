package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.TabelaJogos;
import br.com.lufamador.repository.TabelaJogosRepository;
import br.com.lufamador.validate.TabelaJogosValidate;

@Service
public class TabelaJogosService {

    private final TabelaJogosRepository repository;
    private final TabelaJogosValidate validate;
    private final JogoService jogoService;

    @Autowired
    public TabelaJogosService(TabelaJogosRepository repository, TabelaJogosValidate validate, JogoService jogoService) {
        this.repository = repository;
        this.validate = validate;
        this.jogoService = jogoService;
    }

    public TabelaJogos cadastraTabelaJogos(TabelaJogos tabelaJogos) {
        TabelaJogos tabelaJogosSaved = null;
        try {
            this.jogoService.cadastrarJogos(tabelaJogos.getJogos());
            tabelaJogosSaved = this.repository.saveAndFlush(tabelaJogos);
        } catch (Exception e) {

        }
        return tabelaJogosSaved;
    }

    public List<TabelaJogos> getTabelaJogoss() {
        return this.repository.findAll();
    }

    public final TabelaJogos atualizarTabelaJogos(TabelaJogos tabelaJogos) {
        return this.repository.saveAndFlush(tabelaJogos);
    }

}
