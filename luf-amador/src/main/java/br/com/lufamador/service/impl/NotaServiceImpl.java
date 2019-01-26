package br.com.lufamador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Nota;
import br.com.lufamador.repository.NotaRepository;
import br.com.lufamador.service.NotaService;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepository repository;

    @Override
    public Page<Nota> findAll(int page, int count) {
//        Pageable pages = PageRequest.of(page, count);
//        return this.repository.findAll(pages);
        return null;
    }

    @Override
    public Nota createOrUpdate(Nota entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public Nota findByCodigo(Long codigo) {
//        return this.repository.findById(codigo).get();
    return null;
    }

    @Override
    public Nota findByNumero(String numero) {
        return null;
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
