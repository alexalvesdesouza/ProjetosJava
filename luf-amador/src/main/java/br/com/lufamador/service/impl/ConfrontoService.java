package br.com.lufamador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.repository.ConfrontoRepository;
import br.com.lufamador.validate.ConfrontoValidate;

@Service
public class ConfrontoService {

    private final ConfrontoRepository repository;
    private final ConfrontoValidate validate;

    @Autowired
    public ConfrontoService(ConfrontoRepository repository, ConfrontoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Jogo cadastraConfronto(Jogo jogo) {
        Jogo jogoSaved = null;
        this.validate.validaConfrontoExistente(jogo);
        try {
            jogoSaved = this.repository.saveAndFlush(jogo);
        } catch (Exception e) {
            System.out.println(e);
        }
        return jogoSaved;
    }
}
