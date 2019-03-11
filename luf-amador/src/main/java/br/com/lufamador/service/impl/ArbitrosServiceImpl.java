package br.com.lufamador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufamador.repository.ArbitroRepository;
import br.com.lufamador.service.ArbitroService;

@Service
public class ArbitrosServiceImpl implements ArbitroService {

    private final ArbitroRepository repository;

    @Autowired
    public ArbitrosServiceImpl(ArbitroRepository repository) {
        this.repository = repository;
    }

    private Arbitro create(Arbitro arbitro) {
        Arbitro arbitroSaved = null;
        try {
            arbitroSaved = this.repository.saveAndFlush(arbitro);
        } catch (Exception e) {
        }
        return arbitroSaved;
    }

    private Arbitro update(Arbitro arbitro) {
        return this.repository.saveAndFlush(arbitro);
    }

    public List<Arbitro> getArbitroList() {
        return this.repository.findAll();
    }

    public void excluirEscala(final Long codigo) {
        Arbitro byCodigo = this.repository.findByCodigo(codigo);
        this.repository.delete(byCodigo);
    }

    @Override
    public Page<Arbitro> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nome");
        return this.repository.findAll(pages);
    }

    @Override
    public List<Arbitro> getArbitros() {
        return this.repository.findAll();
    }

    @Override
    public Arbitro createOrUpdate(Arbitro entity) {
        if (null != entity.getCodigo())
            return this.update(entity);
        return this.create(entity);
    }

    @Override
    public Arbitro findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
