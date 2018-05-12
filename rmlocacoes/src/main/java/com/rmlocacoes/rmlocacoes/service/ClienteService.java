package com.rmlocacoes.rmlocacoes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmlocacoes.rmlocacoes.model.Cliente;
import com.rmlocacoes.rmlocacoes.repository.ClienteRepository;
import com.rmlocacoes.rmlocacoes.util.UtilsData;

@Service
public class ClienteService {

  private ClienteRepository     clienteRepository;
  private ContatoService        contatoService;
  private EnderecoService       enderecoService;
  private DadosBancariosService dadosBancariosService;

  @Autowired
  public ClienteService(ClienteRepository clienteRepository, ContatoService contatoService, EnderecoService enderecoService,
      DadosBancariosService dadosBancariosService) {

    this.clienteRepository = clienteRepository;
    this.contatoService = contatoService;
    this.enderecoService = enderecoService;
    this.dadosBancariosService = dadosBancariosService;

  }

  public Cliente buscaCliente(final Long codigo) {
    return this.clienteRepository.findByCodigo(codigo);
  }

  public Cliente salvaCliente(final Cliente cliente) {

    if (cliente.getDadosBancarios() != null) {
      this.dadosBancariosService.salvaDadosBancarios(cliente.getDadosBancarios());
    }

    if (cliente.getContatos() != null && !cliente.getContatos().isEmpty()) {

      cliente.getContatos()
             .forEach(contato -> {
               contatoService.salvaContato(contato);
             });
    }

    if (cliente.getEnderecos() != null && !cliente.getEnderecos().isEmpty()) {

      cliente.getEnderecos()
             .forEach(endereco -> {
               enderecoService.salvaEndereco(endereco);
             });

    }

    parseDataNascimento(cliente);
    Cliente save = clienteRepository.save(cliente);
    return save;
  }

  private void parseDataNascimento(Cliente cliente) {

    final String dta = cliente.getDtaNascimento();
    if (dta != null && !dta.isEmpty()) {
      LocalDateTime dateTimeFormatted = UtilsData.getDateTimeFormatted(dta, "/");
      cliente.setDataNascimento(dateTimeFormatted);

    }
    cliente.setDataCadastro(LocalDateTime.now());

  }

  public List<Cliente> getClientes() {
    List<Cliente> findAll = this.clienteRepository.findAll();

    findAll.forEach(cliente -> {
      LocalDateTime dataNascimento = cliente.getDataNascimento();
      if (dataNascimento != null) {
        String data = UtilsData.getLocalDateTimeToString(dataNascimento, "US", "dd/mm/yyyy");
        cliente.setDtaNascimento(data);        
      }
    });


    return findAll;
  }

}
