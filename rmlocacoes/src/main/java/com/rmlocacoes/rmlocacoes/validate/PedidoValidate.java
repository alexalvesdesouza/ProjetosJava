package com.rmlocacoes.rmlocacoes.validate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.rmlocacoes.rmlocacoes.model.Pedido;
import com.rmlocacoes.rmlocacoes.util.ValidateException;

@Component
public class PedidoValidate {

  private final String MS_CONFLITO_DATA_ANTERIOR = "Data de ? não pode ser anterior à data Atual";

  public void validaPedido(Pedido pedido) {

    this.validaDevolucaoAntesEntrega(pedido);
    this.validaVencimento(pedido);

  }

  private void validaVencimento(Pedido pedido) {
    LocalDateTime agora = LocalDateTime.now();
  }

  private void validaDevolucaoAntesEntrega(Pedido pedido) {

    LocalDate agora = LocalDateTime.now()
                                   .toLocalDate();

    final LocalDateTime dataDevolucao = pedido.getDataDevolucao();
    final LocalDateTime dataEntrega = pedido.getDataEntrega();
    final LocalDateTime dataCancelamento = pedido.getDataCancelamento();

    if (dataDevolucao != null) {
      LocalDate localDate = dataDevolucao.toLocalDate();
      if (agora.isAfter(localDate))
        throw new ValidateException(MS_CONFLITO_DATA_ANTERIOR.replace("?", "Devolução"));
    }
    if (dataEntrega != null) {
      LocalDate localDate = dataEntrega.toLocalDate();
      if (agora.isAfter(localDate))
        throw new ValidateException(MS_CONFLITO_DATA_ANTERIOR.replace("?", "Entrega"));
    }
    if (dataCancelamento != null) {
      LocalDate localDate = dataCancelamento.toLocalDate();
      if (agora.isAfter(localDate))
        throw new ValidateException(MS_CONFLITO_DATA_ANTERIOR.replace("?", "Cancelamento"));
    }

  }

}
