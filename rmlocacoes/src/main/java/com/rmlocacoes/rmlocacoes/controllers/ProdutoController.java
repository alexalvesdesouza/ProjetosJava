package com.rmlocacoes.rmlocacoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmlocacoes.rmlocacoes.model.Produto;
import com.rmlocacoes.rmlocacoes.service.ProdutoService;

@Controller
public class ProdutoController {


  @Autowired
  private ProdutoService produtoService;

  @RequestMapping(value = "/cadastrarProduto", method = RequestMethod.GET)
  public String eventoFormList() {
    return "produto/formProduto";
  }

  @RequestMapping(value = "/controle-estoque", method = RequestMethod.GET)
  public String controleDeEstoque() {
    return "produto/controleEstoque";
  }

  @GetMapping(path = "/carregarProdutos")
  public @ResponseBody Iterable<Produto> carregarProdutos() {
    return produtoService.getProdutos();
  }

  @PostMapping(path = "/cadastrarProduto")
  public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {

    final Produto produtoSaved = produtoService.salvaProduto(produto);
    HttpStatus status = (null == produtoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;

    return new ResponseEntity<Produto>(produtoSaved,
                                       status);
  }

  @PutMapping(path = "/editarProduto")
  public ResponseEntity<Produto> editarProduto(@RequestBody Produto produto) {

    final Produto produtoSaved = produtoService.salvaProduto(produto);
    HttpStatus status = (null == produtoSaved) ? HttpStatus.CONFLICT : HttpStatus.CREATED;

    return new ResponseEntity<Produto>(produtoSaved,
                                       status);
  }

  @PutMapping(path = "/estoque-produto/atualizar")
  public ResponseEntity<Produto> ajustarEstoque(@RequestBody Produto produto) {

    final Produto produtoSaved = produtoService.salvaProduto(produto);
    HttpStatus status = (null == produtoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Produto>(produtoSaved,
                                       status);
  }

  @GetMapping(path = "/produtos")
  public String pedidosList() {
    return "produto/produtosList";
  }

}
