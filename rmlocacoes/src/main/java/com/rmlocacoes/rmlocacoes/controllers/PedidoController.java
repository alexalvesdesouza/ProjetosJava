package com.rmlocacoes.rmlocacoes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rmlocacoes.rmlocacoes.export.ExcelReportView;
import com.rmlocacoes.rmlocacoes.model.Pedido;
import com.rmlocacoes.rmlocacoes.service.PedidoService;
import com.rmlocacoes.rmlocacoes.util.StatusPedido;

@Controller
public class PedidoController {

  private PedidoService pedidoService;

  @Autowired
  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @RequestMapping(value = "/cadastrarPedido", method = RequestMethod.GET)
  public String eventoFormList() {
    return "pedido/formPedido";
  }

  @PostMapping(path = "/cadastrarPedido")
  public ResponseEntity<Pedido> cadastrarPedido(@RequestBody Pedido pedido) {

    final Pedido pedidoSaved = pedidoService.salvaPedido(pedido);
    HttpStatus status = (null == pedidoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Pedido>(pedidoSaved,
                                      status);
  }

  @GetMapping(path = "/carregarPedidos")
  public @ResponseBody Iterable<Pedido> carregarPedidos() {
    return pedidoService.getPedidos();
  }

  @PutMapping(path = "/pedido/{codigo}/registrar-entrega")
  public ResponseEntity<Pedido> registrarEntregaPedido(@PathVariable Long codigo) {

    Pedido buscaPedido = pedidoService.buscaPedido(codigo);
    HttpStatus status = (null == buscaPedido) ? HttpStatus.NOT_FOUND : HttpStatus.OK;

    if (null == buscaPedido) {
      return new ResponseEntity<Pedido>(buscaPedido,
                                        status);
    }
    pedidoService.registraEntregaPedido(buscaPedido);

    return new ResponseEntity<Pedido>(buscaPedido,
                                      status);
  }

  @PutMapping(path = "/pedido/finalizar")
  public ResponseEntity<Pedido> registrarEntregaPedido(@RequestBody Pedido pedido) {

    Pedido buscaPedido = pedidoService.finalizarPedido(pedido);
    HttpStatus status = (null == buscaPedido) ? HttpStatus.NOT_FOUND : HttpStatus.OK;

    return new ResponseEntity<Pedido>(buscaPedido,
                                      status);
  }

  @PostMapping(path = "/detalhesDoPedido")
  public ResponseEntity<Pedido> detalhesDoPedido(@RequestBody Pedido pedido) {

    final Pedido pedidoSaved = pedidoService.salvaPedido(pedido);
    HttpStatus status = (null == pedidoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Pedido>(pedidoSaved,
                                      status);
  }

  @PutMapping(path = "/editarPedido")
  public ResponseEntity<Pedido> editarPedido(@RequestBody Pedido pedido) {

    final Pedido pedidoSaved = pedidoService.salvaPedido(pedido);
    HttpStatus status = (null == pedidoSaved) ? HttpStatus.CONFLICT : HttpStatus.OK;

    return new ResponseEntity<Pedido>(pedidoSaved,
                                      status);
  }

  @GetMapping(path = "/pedidos")
  public String pedidosList() {
    return "pedido/pedidosList";
  }

  @RequestMapping(path = "/report", method = RequestMethod.GET)
  public ModelAndView getExcel() {

    final List<Pedido> pedidos = pedidoService.getPedidos();
    final List<Pedido> pedidosList = pedidos.stream()
                                            .filter(p -> p.getStatus()
                                                          .equals(StatusPedido.AGUARDANDO_ENTREGA.name()
                                                                                                 .replace("_", " ")))
                                            .collect(Collectors.toList());

    return new ModelAndView(new ExcelReportView(),
                            "pedidosList",
                            pedidosList);
  }

}
