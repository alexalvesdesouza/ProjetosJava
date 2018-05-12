package com.rmlocacoes.rmlocacoes.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Item;
import com.rmlocacoes.rmlocacoes.model.Produto;
import com.rmlocacoes.rmlocacoes.repository.ItemRepository;
import com.rmlocacoes.rmlocacoes.util.StatusPedido;

@Service
public class ItemService {

  private ItemRepository itemRepository;
  private ProdutoService produtoService;


  @Autowired
  public ItemService(ItemRepository itemRepository, ProdutoService produtoService) {
    this.itemRepository = itemRepository;
    this.produtoService = produtoService;
  }

  public Item salvaItem(final Item entity, final Map<String, Boolean> statusPedido) {
    this.somaTotalItem(entity);
    Item save = itemRepository.save(entity);
    atualizaEstoqueProduto(entity, statusPedido);
    return save;
  }

  private void atualizaEstoqueProduto(final Item entity, final Map<String, Boolean> statusPedido) {

    Boolean isPedidoFinalizado = statusPedido.get(StatusPedido.FINALIZADO.name());
    Boolean isPedidoCancelado = statusPedido.get(StatusPedido.CANCELADO.name());
    Boolean isRegistroEntrega = statusPedido.get(StatusPedido.ENTREGUE.name());

    if (isRegistroEntrega && !isPedidoFinalizado) {
      return;
    }

    final Produto produto = entity.getProduto();
    final Long quantidadeVendida = entity.getQuantidade();
    Long quantidadeAtualizada = 0L;
    Long quantidadeEstoque = produto.getQuantidade();

    if (isPedidoCancelado || isPedidoFinalizado) {
      if ("REVENDA".equals(produto.getCategoria())) {
        return;
      }
      quantidadeAtualizada = quantidadeEstoque + quantidadeVendida;
    } else {
      quantidadeAtualizada = quantidadeEstoque - quantidadeVendida;
    }

    produtoService.ajustarEstoqueProduto(produto, quantidadeAtualizada);
  }

  private void somaTotalItem(Item item) {

    BigDecimal qtdItem = new BigDecimal(item.getQuantidade());
    BigDecimal precoItem = item.getProduto()
                               .getPreco();
    BigDecimal totalItem = qtdItem.multiply(precoItem);

    item.setVlrUnitario(precoItem);
    item.setTotalItem(totalItem);
  }

  public Iterable<Item> getItens() {
    Iterable<Item> findAll = this.itemRepository.findAll();
    return findAll;
  }

}
