package com.algaworks.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.exceptions.PessoaInexistenteOuInativaException;
import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.filter.LancamentoFilter;
import com.algaworks.algamoneyapi.repository.projection.ResumoLancamento;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private PessoaService pessoaService;

    public Page<Lancamento> listaTodasLancamentos(LancamentoFilter filter, Pageable pageable) {
        return this.lancamentoRepository.filtrar(filter, pageable);
    }

    public Page<ResumoLancamento> resumo(LancamentoFilter filter, Pageable pageable) {
        return this.lancamentoRepository.resumir(filter, pageable);
    }

    public Lancamento cadastraLancamento(Lancamento entity) {
        final Pessoa pessoa = this.pessoaService.buscaPessoa(entity.getPessoa().getCodigo());
        if (pessoa == null || pessoa.isInativa()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return this.lancamentoRepository.save(entity);
    }

    public Lancamento buscaLancamento(Long codigo) {
        Lancamento lancamento = this.lancamentoRepository.findOne(codigo);

        if (lancamento == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return lancamento;
    }

    public void deletaLancamento(Long codigo) {
        this.lancamentoRepository.delete(codigo);
    }

    public ResponseEntity<Lancamento> atulizaLancamento(Long codigo, Lancamento lancamento) {
        Lancamento lancamentoSalva = this.buscaLancamento(codigo);
        BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");

        this.lancamentoRepository.save(lancamentoSalva);
        return ResponseEntity.ok(lancamentoSalva);
    }

}
