package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
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

        if (!pessoa.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        return pessoa.get();
    }

    public void deletaPessoa(Long codigo) {
        this.pessoaRepository.deleteById(codigo);
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
