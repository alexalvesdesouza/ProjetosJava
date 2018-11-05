package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listaTodasPessoas() {
        return this.pessoaRepository.findAll();
    }

    public Pessoa cadastraPessoa(Pessoa entity) {
        return this.pessoaRepository.save(entity);
    }

    public Pessoa buscaPessoa(Long codigo) {
        Optional<Pessoa> pessoa = this.pessoaRepository.findById(codigo);
        if (pessoa.isPresent()) {
            return pessoa.get();
        }

        return null;
    }

    public void deletaPessoa(Long codigo) {
        this.pessoaRepository.deleteById(codigo);
    }
}
