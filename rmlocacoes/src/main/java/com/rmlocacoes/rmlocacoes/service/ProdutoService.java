package com.rmlocacoes.rmlocacoes.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Produto;
import com.rmlocacoes.rmlocacoes.repository.ProdutoRepository;

@Service
public class ProdutoService {

  private ProdutoRepository produtoRepository;

  @Autowired
  public ProdutoService(ProdutoRepository produtoRepository) {
    this.produtoRepository = produtoRepository;
  }

  public Produto buscaProduto(final Long codigo) {
    return this.produtoRepository.findByCodigo(codigo);
  }

  public Produto salvaProduto(final Produto produto) {
    produto.setDataCadastro(LocalDateTime.now());
    Produto save = produtoRepository.save(produto);
    return save;
  }

  private Produto ajustaEstoque(final Produto produto) {
    Produto save = produtoRepository.save(produto);
    return save;
  }

  public Iterable<Produto> getProdutos() {
    Iterable<Produto> findAll = this.produtoRepository.findAll();
    return findAll;
  }

  public void ajustarEstoqueProduto(Produto produto, Long quantidadeAtualizada) {  
    produto.setQuantidade(quantidadeAtualizada);
    this.ajustaEstoque(produto);    
  }

}
