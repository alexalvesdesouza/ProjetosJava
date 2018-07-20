package br.com.lufamador.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Tjdu;
import br.com.lufamador.repository.TjduRepository;
import br.com.lufamador.service.TjduService;
import br.com.lufamador.validate.TjduValidate;

@Service
public class TjduServiceImpl implements TjduService {

    private final TjduRepository repository;
    private final TjduValidate validate;

    @Autowired
    public TjduServiceImpl(TjduRepository repository, TjduValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    private Tjdu create(Tjdu tjdu) {
        Tjdu tjduSaved = null;
        tjdu.setCategoria(tjdu.getCategoria().toUpperCase());
        this.validate.validaCadastroTJDU(tjdu);
        try {
            tjduSaved = this.repository.saveAndFlush(tjdu);
        } catch (Exception e) {
        }
        return tjduSaved;
    }

    private Tjdu update(Tjdu tjdu) {
        Tjdu tjduSaved = null;
        //this.validate.validaCadastroTJDU(tjdu);
        tjdu.setCategoria(tjdu.getCategoria().toUpperCase());
        try {
            tjduSaved = this.repository.saveAndFlush(tjdu);
        } catch (Exception e) {
        }
        return tjduSaved;
    }

    public List<Tjdu> getTjduList(final String categoria) {
        return this.repository.findByCategoria(categoria);
    }

    private void deletarTjdu(final Long codigo) {
        Optional<Tjdu> tjud = this.repository.findById(codigo);
        if (tjud.isPresent())
            this.repository.delete(tjud.get());
        //TODO criar classe de BussinessException
    }

    @Override
    public Page<Tjdu> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        return this.repository.findAll(pages);
    }

    @Override
    public Tjdu createOrUpdate(Tjdu entity) {
        if (null != entity.getCodigo())
            return this.update(entity);
        return this.create(entity);
    }

    @Override
    public Tjdu findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long codigo) {
        this.deletarTjdu(codigo);
    }
}
