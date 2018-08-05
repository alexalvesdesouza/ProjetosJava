package br.com.lufamador.service;

import org.springframework.data.domain.Page;

import br.com.lufamador.model.Produto;

public interface ProdutoService {

    Page<Produto> findAll(int page, int count);

    Produto createOrUpdate(Produto entity);

    Produto findByCodigo(Long codigo);

    void delete(Long codigo);
}
