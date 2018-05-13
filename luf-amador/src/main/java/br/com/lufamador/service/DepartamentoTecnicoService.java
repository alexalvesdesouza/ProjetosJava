package br.com.lufamador.service;

import static br.com.lufamador.utils.mensagens.MensagensErro.ENTIDADE_INEXISTENTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
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
        departamentoTecnico.setCategoria(departamentoTecnico.getCategoria().toUpperCase());
        this.validate.validaCadastroTJDU(departamentoTecnico);
        try {
            departamentoTecnicoSaved = this.repository.saveAndFlush(departamentoTecnico);
        } catch (Exception e) {
        }
        return departamentoTecnicoSaved;
    }

    public DepartamentoTecnico editarDepartamentoTecnico(DepartamentoTecnico departamentoTecnico) {
        DepartamentoTecnico departamentoTecnicoSaved = null;
        //this.validate.validaCadastroTJDU(departamentoTecnico);
        departamentoTecnico.setCategoria(departamentoTecnico.getCategoria().toUpperCase());
        try {
            departamentoTecnicoSaved = this.repository.saveAndFlush(departamentoTecnico);
        } catch (Exception e) {
        }
        return departamentoTecnicoSaved;
    }

    public List<DepartamentoTecnico> getDepartamentoTecnicoList(String categoria) {
        return this.repository.findByCategoria(categoria.replace("_", "-"));
    }

    public void deletarEntidadeDepartamentoTecnico(final Long codigo) {
        Optional<DepartamentoTecnico> tjud = this.repository.findById(codigo);
        if (!tjud.isPresent()) {
            throw new BussinessException(ENTIDADE_INEXISTENTE.replace("?", "Entidade"));
        }
        this.repository.delete(tjud.get());

    }
}
