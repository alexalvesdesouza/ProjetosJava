package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.repository.EscalaArbitrosRepository;
import br.com.lufamador.service.EscalaService;
import br.com.lufamador.validate.EscalaArbitrosValidate;

@Service
public class EscalaArbitrosServiceImpl implements EscalaService {

    private final EscalaArbitrosRepository repository;
    private final EscalaArbitrosValidate validate;

    @Autowired
    public EscalaArbitrosServiceImpl(EscalaArbitrosRepository repository, EscalaArbitrosValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    private EscalaArbitros create(EscalaArbitros escalaArbitros) {
        EscalaArbitros escalaArbitrosSaved = null;
        this.validate.validaEscalaArbitrosExistente(escalaArbitros);
        try {
            if (null == escalaArbitros.getTemporada()) {
                escalaArbitros.setTemporada(String.valueOf(LocalDate.now().getYear()));
            }
            escalaArbitrosSaved = this.repository.saveAndFlush(escalaArbitros);
        } catch (Exception e) {
        }
        return escalaArbitrosSaved;
    }

    private EscalaArbitros update(EscalaArbitros escalaArbitros) {
        if (null == escalaArbitros.getTemporada()) {
            escalaArbitros.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }
        return this.repository.saveAndFlush(escalaArbitros);
    }

    public List<EscalaArbitros> getEscalaArbitrosList() {
        return this.repository.findAll();
    }

    public void excluirEscala(final Long codigo) {
        EscalaArbitros byCodigo = this.repository.findByCodigo(codigo);
        this.repository.delete(byCodigo);
    }

    @Override
    public Page<EscalaArbitros> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        return this.repository.findAll(pages);
    }

    @Override
    public EscalaArbitros createOrUpdate(EscalaArbitros entity) {
        if (null != entity.getCodigo())
            return this.update(entity);
        return this.create(entity);
    }

    @Override
    public EscalaArbitros findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
