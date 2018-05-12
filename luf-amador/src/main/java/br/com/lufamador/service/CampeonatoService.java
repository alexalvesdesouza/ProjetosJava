package br.com.lufamador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Campeonato;
import br.com.lufamador.model.Inscricao;
import br.com.lufamador.repository.CampeonatoRepository;
import br.com.lufamador.validate.CampeonatoValidate;

@Service
public class CampeonatoService {

    private final CampeonatoRepository repository;
    private final CampeonatoValidate validate;

    @Autowired
    public CampeonatoService(CampeonatoRepository repository, CampeonatoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Campeonato cadastraCampeonato(Campeonato campeonato) {
        Campeonato campeonatoSaved = null;
        this.validate.validaCampeonatoExistente(campeonato);
        try {
            campeonatoSaved = this.repository.saveAndFlush(campeonato);
        } catch (Exception e) {

        }
        return campeonatoSaved;
    }

    public List<Campeonato> getCampeonatos() {
        return this.repository.findAll();
    }

    public final Campeonato atualizarCampeonato(Campeonato campeonato) {
        return this.repository.saveAndFlush(campeonato);
    }

}
