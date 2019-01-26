package br.com.lufamador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Produto;
import br.com.lufamador.repository.ProdutoRepository;
import br.com.lufamador.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Override
    public Page<Produto> findAll(int page, int count) {
//        Pageable pages = PageRequest.of(page, count);
//        return this.repository.findAll(pages);
        return null;
    }

    @Override
    public Produto createOrUpdate(Produto entity) {
        return this.repository.saveAndFlush(entity);
    }

    @Override
    public Produto findByCodigo(Long codigo) {
//        return this.repository.findById(codigo).get();
    return null;
    }

    @Override
    public void delete(Long codigo) {
        this.repository.delete(this.findByCodigo(codigo));
    }
}
