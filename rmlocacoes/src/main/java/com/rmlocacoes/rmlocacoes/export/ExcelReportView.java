package com.rmlocacoes.rmlocacoes.export;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.rmlocacoes.rmlocacoes.model.Endereco;
import com.rmlocacoes.rmlocacoes.model.Pedido;

public class ExcelReportView extends AbstractXlsView {

  int rowNum = 1;

  @Override
  protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
      throws Exception {

    response.setHeader("Content-Disposition", "attachment;filename=\"pedidos.xls\"");
    List<Pedido> pedidosList = (List<Pedido>) model.get("pedidosList");
    Sheet sheet = workbook.createSheet("Pedido Data");
    Row header = sheet.createRow(0);
    header.createCell(0)
          .setCellValue("CODIGO");
    header.createCell(1)
          .setCellValue("STATUS");
    header.createCell(2)
          .setCellValue("CLIENTE");
    header.createCell(3)
          .setCellValue("ENDERECO");
    header.createCell(4)
          .setCellValue("OBSERVACAO");

    pedidosList.forEach(pedido -> {

      final String enderecoCompleto = this.getEnderecoCompletoFormatado(pedido);

      Row row = sheet.createRow(rowNum++);
      row.createCell(0)
         .setCellValue(pedido.getCodigo());
      row.createCell(1)
         .setCellValue(pedido.getStatus());
      row.createCell(2)
         .setCellValue(pedido.getCliente()
                             .getNome());
      row.createCell(3)
         .setCellValue(enderecoCompleto);
      row.createCell(4)
         .setCellValue(pedido.getObservacao());

    });
  }

  private final String getEnderecoCompletoFormatado(final Pedido pedido) {
    StringBuffer sb = new StringBuffer();

    Optional<Endereco> endereco = pedido.getCliente()
                                        .getEnderecos()
                                        .stream()
                                        .filter(end -> end.getEnderecoEntrega())
                                        .findFirst();
    if (endereco.isPresent()) {
      sb.append(endereco.get()
                        .getLogradouro())
        .append(" n° ")
        .append(endereco.get()
                        .getNumero())
        .append(" - CEP: ")
        .append(endereco.get()
                        .getCep())
        .append(" - Bairro: ")
        .append(endereco.get()
                        .getBairro())
        .append(" - ")
        .append(endereco.get()
                        .getCidade())
        .append(" / ")
        .append(endereco.get()
                        .getEstado())
        .append(" -- Complemento: ( ")
        .append(endereco.get()
                        .getComplemento() != null
                            ? endereco.get()
                                      .getComplemento()
                            : "")
        .append(" )");

    }

    return sb.toString();
  }


}
