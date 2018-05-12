package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Local;
import br.com.lufamador.repository.LocalRepository;
import br.com.lufamador.validate.LocalValidate;

@Service
public class LocalService {

    private final LocalRepository repository;
    private final LocalValidate validate;

    @Autowired
    public LocalService(LocalRepository repository, LocalValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Local cadastraLocal(Local local) {
        Local localSaved = null;
        this.validate.validaLocalExistente(local);
        try {
            localSaved = this.repository.saveAndFlush(local);
        } catch (Exception e) {
        }
        return localSaved;
    }

    public List<Local> getLocais() {
        return this.repository.findAll();
    }
}
