package br.com.lufamador.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.repository.LocalJogoRepository;
import br.com.lufamador.service.LocalJogoService;
import br.com.lufamador.utils.mensagens.MensagensErro;

@Service
public class LocalJogoServiceImpl implements LocalJogoService {

    private final LocalJogoRepository repository;

    @Autowired
    public LocalJogoServiceImpl(LocalJogoRepository repository) {
        this.repository = repository;
    }

    private LocalJogo cadastraLocalJogo(LocalJogo localJogo) {
        return this.repository.saveAndFlush(localJogo);
    }


    private LocalJogo atualizaLocalJogo(LocalJogo localJogo) {
        return this.repository.saveAndFlush(localJogo);
    }

    public void deletarLocalJogo(final Long codigo) {
        final LocalJogo localJogo = this.getLocalJogo(codigo);
        this.repository.delete(localJogo);
    }

    public LocalJogo getLocalJogo(final Long codigo) {
        Optional<LocalJogo> localJogo = this.repository.findById(codigo);
        if (!localJogo.isPresent())
            throw new BussinessException(MensagensErro.ENTIDADE_INEXISTENTE);
        return localJogo.get();
    }

    @Override
    public Page<LocalJogo> findAll(int page, int count) {
        Pageable pages = PageRequest.of(page, count, Sort.Direction.ASC, "nome");
        return this.repository.findAll(pages);
    }

    public Map<String, LocalJogo> findAllMap(int page, int count) {
        Pageable pages = PageRequest.of(page, count);
        Page<LocalJogo> all = this.repository.findAll(pages);

        Map<String, LocalJogo> map = new HashMap<>();
        all.getContent().forEach(localJogo -> {
            map.put(localJogo.getNome(), localJogo);
        });

        return map;
    }

    @Override
    public LocalJogo createOrUpdate(LocalJogo localJogo) {
        if (localJogo.getCodigo() != null)
            return this.atualizaLocalJogo(localJogo);
        return this.cadastraLocalJogo(localJogo);
    }

    @Override
    public LocalJogo findByCodigo(Long codigo) {
        return this.repository.findById(codigo).get();
    }

    @Override
    public void delete(Long id) {
        this.deletarLocalJogo(id);
    }
}
