package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exceptions.PessoaInexistenteOuInativaException;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaService pessoaService;

    public Page<Lancamento> listaTodasLancamentos(LancamentoFilter filter, Pageable pageable) {
        return this.lancamentoRepository.filtrar(filter, pageable);
    }

    public Lancamento cadastraLancamento(Lancamento entity) {
        final Pessoa pessoa = this.pessoaService.buscaPessoa(entity.getPessoa().getCodigo());
        if (pessoa == null || pessoa.isInativa()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return this.lancamentoRepository.save(entity);
    }

    public Lancamento buscaLancamento(Long codigo) {
        Optional<Lancamento> lancamento = this.lancamentoRepository.findById(codigo);

        if (!lancamento.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }

        return lancamento.get();
    }

    public void deletaLancamento(Long codigo) {
        this.lancamentoRepository.deleteById(codigo);
    }

    public ResponseEntity<Lancamento> atulizaLancamento(Long codigo, Lancamento lancamento) {
        Lancamento lancamentoSalva = this.buscaLancamento(codigo);
        BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");

        this.lancamentoRepository.save(lancamentoSalva);
        return ResponseEntity.ok(lancamentoSalva);
    }

}
