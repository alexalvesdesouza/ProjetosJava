package com.algaworks.algamoneyapi.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

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
       Pessoa pessoa = this.pessoaRepository.findOne(codigo);

        if (pessoa == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return pessoa;
    }

    public void deletaPessoa(Long codigo) {
        this.pessoaRepository.delete(codigo);
    }

    public ResponseEntity<Pessoa> atulizaPessoa(Long codigo, Pessoa pessoa) {
        Pessoa pessoaSalva = this.buscaPessoa(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

        this.pessoaRepository.save(pessoaSalva);
        return ResponseEntity.ok(pessoaSalva);
    }

    public void ativaInativaPessoa(Long codigo, Boolean ativa) {
        Pessoa pessoa = this.buscaPessoa(codigo);
        pessoa.setAtiva(ativa);
        this.pessoaRepository.save(pessoa);
    }
}
