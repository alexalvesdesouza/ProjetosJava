package com.rmlocacoes.rmlocacoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Pedido;
import com.rmlocacoes.rmlocacoes.model.Receber;
import com.rmlocacoes.rmlocacoes.repository.ReceberRepository;
import com.rmlocacoes.rmlocacoes.util.BussinessForbbidenException;
import com.rmlocacoes.rmlocacoes.util.StatusEntidade;

@Service
public class ReceberService {

  private ReceberRepository     receberRepository;
  private ContaVinculadaService contasVinculadaService;

  public ReceberService(ReceberRepository pagarRepository, ContaVinculadaService contasVinculadaService) {
    super();
    this.receberRepository = pagarRepository;
    this.contasVinculadaService = contasVinculadaService;
  }

  public Receber geraRecebimentoPedido(Pedido pedido) {

    Receber receber = new Receber();
    receber.setDescricao("A receber do cliente " + pedido.getCliente()
                                                         .getNome());
    receber.setNumeroDocumento(pedido.getCodigo()
                                     .toString());
    receber.setStatus(StatusEntidade.A_RECEBER.name());
    receber.setValor(pedido.getTotalPedido());
    receber.setObservacao(pedido.getObservacao());

    receber = receberRepository.saveAndFlush(receber);
    return receber;
  }

  public Receber efetivaRecebimento(Long codigo) {

    Receber receber = this.receberRepository.findByCodigo(codigo);
    this.validaStatus(receber);
    receber.setStatus(StatusEntidade.RECEBIDO.name());
    receber = receberRepository.saveAndFlush(receber);
    this.contasVinculadaService.atualizaSaldo(receber.getValor(), true);
    return receber;
  }

  private void validaStatus(Receber receber) {
    if (receber.getStatus()
               .equals(StatusEntidade.RECEBIDO.name()))
      throw new BussinessForbbidenException();
  }

  public List<Receber> getRecebimentos() {
    return this.receberRepository.findAll();
  }


}
