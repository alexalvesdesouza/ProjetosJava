package br.com.lufamador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.galeria.Foto;
import br.com.lufamador.repository.FotoRepository;
import br.com.lufamador.service.FotoService;

@Service
public class FotoServiceImpl implements FotoService {

    @Autowired
    private FotoRepository repository;

    @Override
    public Page<Foto> findAll(int page, int count) {
//        Pageable pages = PageRequest.of(page, count);
//        return this.repository.findAll(pages);
        return null;
    }

    @Override
    public Foto createOrUpdate(Foto entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public Foto findByCodigo(Long codigo) {
        return this.repository.findByCodigo(codigo);
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
