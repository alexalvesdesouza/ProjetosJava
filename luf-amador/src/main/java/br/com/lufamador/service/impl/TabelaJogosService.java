package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.model.TabelaJogos;
import br.com.lufamador.repository.TabelaJogosRepository;
import br.com.lufamador.validate.TabelaJogosValidate;

@Service
public class TabelaJogosService {

    private final TabelaJogosRepository repository;
    private final TabelaJogosValidate validate;
    private final JogoService jogoService;

    @Autowired
    public TabelaJogosService(TabelaJogosRepository repository, TabelaJogosValidate validate,
            JogoService jogoService) {
        this.repository = repository;
        this.validate = validate;
        this.jogoService = jogoService;
    }

    public TabelaJogos cadastraTabelaJogos(TabelaJogos tabelaJogos) {
        TabelaJogos tabelaJogosSaved = null;
        try {
            this.interfaceCadastraJogos(tabelaJogos);
            tabelaJogosSaved = this.repository.saveAndFlush(tabelaJogos);
        } catch (Exception e) {

        }
        return tabelaJogosSaved;
    }

    public TabelaJogos createOrUdate(TabelaJogos tabelaJogos) {

        if (tabelaJogos.getCodigo() != null)
            return this.atualizarTabelaJogos(tabelaJogos);
        return this.cadastraTabelaJogos(tabelaJogos);
    }

    public List<TabelaJogos> getTabelaJogosPorCampeonato(final Long codigo) {
        return this.repository.findAll();
    }

    private void interfaceCadastraJogos(final TabelaJogos tabelaJogos) {
        final List<Jogo> jogos = tabelaJogos.getJogos();
        if (null != jogos && !jogos.isEmpty()) {
            this.jogoService.cadastraJogo(jogos);
        }

    }

    public final TabelaJogos atualizarTabelaJogos(TabelaJogos tabelaJogos) {
        this.interfaceCadastraJogos(tabelaJogos);
        return this.repository.saveAndFlush(tabelaJogos);
    }

}
