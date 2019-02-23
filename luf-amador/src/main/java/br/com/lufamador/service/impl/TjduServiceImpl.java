package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.MembroTjdu;
import br.com.lufamador.model.Tjdu;
import br.com.lufamador.repository.MembroTjduRepository;
import br.com.lufamador.repository.TjduRepository;
import br.com.lufamador.service.TjduService;
import br.com.lufamador.validate.TjduValidate;

@Service
public class TjduServiceImpl implements TjduService {

    private final TjduRepository repository;
    private final MembroTjduRepository repositoryMembrosTjdu;
    private final TjduValidate validate;

    @Autowired
    public TjduServiceImpl(TjduRepository repository, TjduValidate validate,
            MembroTjduRepository repositoryMembrosTjdu) {
        this.repository = repository;
        this.validate = validate;
        this.repositoryMembrosTjdu = repositoryMembrosTjdu;
    }

    private Tjdu create(Tjdu tjdu) {
        Tjdu tjduSaved = null;
        tjdu.setCategoria(tjdu.getCategoria().toUpperCase());
        this.validate.validaCadastroTJDU(tjdu);
        try {
            if (null == tjdu.getTemporada()) {
                tjdu.setTemporada(String.valueOf(LocalDate.now().getYear()));
            }
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
            if (null == tjdu.getTemporada()) {
                tjdu.setTemporada(String.valueOf(LocalDate.now().getYear()));
            }
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
        if (null == entity.getTemporada()) {
            entity.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }
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

    @Override
    public Page<MembroTjdu> findAllMembros(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        return this.repositoryMembrosTjdu.findAll(pages);
    }

    @Override
    public MembroTjdu createOrUpdate(MembroTjdu entity) {
        return this.repositoryMembrosTjdu.saveAndFlush(entity);
    }

    @Override
    public MembroTjdu findMembroByCodigo(Long codigo) {
        return this.repositoryMembrosTjdu.findByCodigo(codigo);
    }

    @Override
    public void deleteMenbroTjdu(Long codigo) {
        this.repositoryMembrosTjdu.delete(this.findMembroByCodigo(codigo));
    }
}
