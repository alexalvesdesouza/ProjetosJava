package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.repository.DepartamentoTecnicoRepository;
import br.com.lufamador.validate.DepartamentoTecnicoValidate;

@Service
public class DepartamentoTecnicoService {

    private final DepartamentoTecnicoRepository repository;
    private final DepartamentoTecnicoValidate validate;

    @Autowired
    public DepartamentoTecnicoService(DepartamentoTecnicoRepository repository, DepartamentoTecnicoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public DepartamentoTecnico cadastraDepartamentoTecnico(DepartamentoTecnico departamentoTecnico) {
        DepartamentoTecnico departamentoTecnicoSaved = null;
        this.validate.validaDepartamentoTecnicoExistente(departamentoTecnico);
        try {
            departamentoTecnicoSaved = this.repository.saveAndFlush(departamentoTecnico);
        } catch (Exception e) {
        }
        return departamentoTecnicoSaved;
    }

    public List<DepartamentoTecnico> getDepartamentosTecnicos() {
        return this.repository.findAll();
    }
}
