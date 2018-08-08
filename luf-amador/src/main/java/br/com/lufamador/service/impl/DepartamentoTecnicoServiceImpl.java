package br.com.lufamador.service.impl;

import static br.com.lufamador.utils.mensagens.MensagensErro.ENTIDADE_INEXISTENTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.repository.DepartamentoTecnicoRepository;
import br.com.lufamador.service.DepartamentoTecnicoService;
import br.com.lufamador.validate.DepartamentoTecnicoValidate;

@Service
public class DepartamentoTecnicoServiceImpl implements DepartamentoTecnicoService {

    private final DepartamentoTecnicoRepository repository;
    private final DepartamentoTecnicoValidate validate;

    @Autowired
    public DepartamentoTecnicoServiceImpl(DepartamentoTecnicoRepository repository,
            DepartamentoTecnicoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    private DepartamentoTecnico create(DepartamentoTecnico departamentoTecnico) {
        DepartamentoTecnico departamentoTecnicoSaved = null;
        departamentoTecnico.setCategoria(departamentoTecnico.getCategoria().toUpperCase());
        this.validate.validaCadastroTJDU(departamentoTecnico);
        try {
            departamentoTecnicoSaved = this.repository.saveAndFlush(departamentoTecnico);
        } catch (Exception e) {
        }
        return departamentoTecnicoSaved;
    }

    private DepartamentoTecnico update(DepartamentoTecnico departamentoTecnico) {
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
        return this.repository.findByCategoria(categoria);
    }

    public void deletarEntidadeDepartamentoTecnico(final Long codigo) {
        Optional<DepartamentoTecnico> tjud = this.repository.findById(codigo);
        if (!tjud.isPresent()) {
            throw new BussinessException(ENTIDADE_INEXISTENTE.replace("?", "Entidade"));
        }
        this.repository.delete(tjud.get());

    }

    @Override
    public Page<DepartamentoTecnico> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        return this.repository.findAll(pages);
    }

    @Override
    public DepartamentoTecnico createOrUpdate(DepartamentoTecnico departamentoTecnico) {
        if (null != departamentoTecnico.getCodigo())
            return this.update(departamentoTecnico);
        return this.create(departamentoTecnico);
    }

    @Override
    public DepartamentoTecnico findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
