package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Contato;
import br.com.lufamador.repository.ContatoRepository;
import br.com.lufamador.validate.ContatoValidate;

@Service
public class ContatoService {

    private final ContatoRepository repository;
    private final ContatoValidate validate;

    @Autowired
    public ContatoService(ContatoRepository repository, ContatoValidate validate) {
        this.repository = repository;
        this.validate = validate;
    }

    public Contato cadastraContato(Contato contato) {
        Contato contatoSaved = null;
        this.validate.validaContatoExistente(contato);
        try {
            contatoSaved = this.repository.saveAndFlush(contato);
        } catch (Exception e) {
            System.out.println(e);
        }
        return contatoSaved;
    }

    public List<Contato> getContatos() {
        return this.repository.findAll();
    }

    public final Contato atulizarContato(Contato contatoAtualizar) {
        return this.repository.saveAndFlush(contatoAtualizar);
    }

}
