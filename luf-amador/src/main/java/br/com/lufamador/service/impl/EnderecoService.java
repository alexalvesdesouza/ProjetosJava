package br.com.lufamador.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Endereco;
import br.com.lufamador.repository.EnderecoRepository;
import br.com.lufamador.validate.EnderecoValidate;

@Service
public class EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoValidate validate;

    @Autowired
    public EnderecoService(EnderecoRepository repository, EnderecoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Endereco cadastraEndereco(Endereco endereco) {
        Endereco enderecoSaved = null;
        this.validate.validaEnderecoExistente(endereco);
        try {
            enderecoSaved = this.repository.saveAndFlush(endereco);
        } catch (Exception e) {

        }
        return enderecoSaved;
    }

    public Endereco atualizaEndereco(Endereco endereco) {
        return this.repository.saveAndFlush(endereco);
    }
}
