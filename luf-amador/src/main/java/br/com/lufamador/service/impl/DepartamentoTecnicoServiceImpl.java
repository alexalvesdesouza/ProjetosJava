package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.lufamador.exceptions.BussinessException;
import br.com.lufamador.model.DepartamentoTecnico;
import br.com.lufamador.repository.DepartamentoTecnicoRepository;
import br.com.lufamador.service.DepartamentoTecnicoService;
import br.com.lufamador.utils.constants.CategoriaConstant;
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
            this.alteraCorDocs(departamentoTecnico);
            departamentoTecnicoSaved = this.repository.saveAndFlush(departamentoTecnico);
        } catch (Exception e) {
            throw new BussinessException(e.getMessage());
        }
        return departamentoTecnicoSaved;
    }

    private void alteraCorDocs(DepartamentoTecnico departamentoTecnico) {
        String cor = "#ff0000";
        if (CategoriaConstant.PORTARIAS.name().equals(departamentoTecnico.getCategoria())) {
            cor = "#b3b3cc";
        }
        if (CategoriaConstant.COMUNICADOS.name().equals(departamentoTecnico.getCategoria())) {
            cor = "#80d4ff";
        }
        if (CategoriaConstant.NOTAS_OFICIAIS.name().equals(departamentoTecnico.getCategoria())) {
            cor = "#ffb84d";
        }

        departamentoTecnico.setCor(cor);
    }

    private DepartamentoTecnico update(DepartamentoTecnico departamentoTecnico) {
        DepartamentoTecnico departamentoTecnicoSaved = null;
        departamentoTecnico.setCategoria(departamentoTecnico.getCategoria().toUpperCase());
        try {
            this.alteraCorDocs(departamentoTecnico);
            departamentoTecnicoSaved = this.repository.saveAndFlush(departamentoTecnico);
        } catch (Exception e) {
            throw new BussinessException(e.getMessage());
        }
        return departamentoTecnicoSaved;
    }

    public List<DepartamentoTecnico> getDepartamentoTecnicoList(String categoria) {
        return this.repository.findByCategoria(categoria);
    }

    public void deletarEntidadeDepartamentoTecnico(final Long codigo) {
//        Optional<DepartamentoTecnico> tjud = this.repository.findById(codigo);
//        if (!tjud.isPresent()) {
//            throw new BussinessException(ENTIDADE_INEXISTENTE.replace("?", "Entidade"));
//        }
//        this.repository.delete(tjud.get());


    }

    @Override
    public Page<DepartamentoTecnico> findAll(int page, int count) {
//        Pageable pages = PageRequest.of(page, count);
//        return this.repository.findAll(pages);
        return null;
    }

    @Override
    public DepartamentoTecnico createOrUpdate(DepartamentoTecnico departamentoTecnico) {
        this.normalizaPaginaInicial(departamentoTecnico);
        this.setSubCategoria(departamentoTecnico);
        if (null != departamentoTecnico.getCodigo())
            return this.update(departamentoTecnico);
        return this.create(departamentoTecnico);
    }

    private void normalizaPaginaInicial(DepartamentoTecnico departamentoTecnico) {
        if (!departamentoTecnico.getCategoria().equals(CategoriaConstant.EDITAIS.name())
                && !departamentoTecnico.getCategoria().equals(CategoriaConstant.NOTAS_OFICIAIS.name())
                && !departamentoTecnico.getCategoria().equals(CategoriaConstant.COMUNICADOS.name())) {
            departamentoTecnico.setExibirPaginaPrincipal(false);
        }

    }

    private void setSubCategoria(DepartamentoTecnico departamentoTecnico) {

        String categoria = departamentoTecnico.getCategoria();
        if (null == departamentoTecnico.getSubCategoria() || "".equals(departamentoTecnico.getSubCategoria())) {
            departamentoTecnico.setSubCategoria(categoria);
        }
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
