package br.com.lufamador.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Inscricao;
import br.com.lufamador.repository.InscricaoRepository;
import br.com.lufamador.validate.InscricaoValidate;

@Service
public class InscricaoService {

    private final InscricaoRepository repository;
    private final InscricaoValidate validate;
    private final AgremiacaoServiceImpl agremiacaoService;

    @Autowired
    public InscricaoService(InscricaoRepository repository, InscricaoValidate validate,
            AgremiacaoServiceImpl agremiacaoService) {
        this.repository = repository;
        this.validate = validate;
        this.agremiacaoService = agremiacaoService;
    }

    public Inscricao cadastraInscricao(Inscricao inscricao) {
        Inscricao inscricaoSaved = null;
        //this.validate.validaInscricaoExistente(inscricao);
        try {
            inscricaoSaved = this.repository.saveAndFlush(inscricao);
            //this.campeonatoService.insereInscritos(inscricaoSaved);
        } catch (Exception e) {

        }
        return inscricaoSaved;
    }

    public boolean deletarInscricoes(List<Inscricao> inscricoes) {
        inscricoes.stream().forEach(inscricao -> {
            this.repository.delete(inscricao);
        });

        return inscricoes.isEmpty();
    }
    
    public void deletaInscricao(Inscricao inscricao) {
      this.repository.delete(inscricao);
    }

    public List<Inscricao> getInscricoes() {
        return this.repository.findAll();
    }

    public final Inscricao atualizarInscricao(Inscricao inscricao) {
        return this.repository.saveAndFlush(inscricao);
    }

    public void excluirInscricao(final Long codigo) {
        final Optional<Inscricao> inscricao = this.repository.findById(codigo);

        this.repository.delete(inscricao.get());
    }

    public final Inscricao inscricaoAgremiacaoCampeonato(final Long codigoAgremiacao) {
        Inscricao inscricao = new Inscricao();
        inscricao.setAgremiacao(this.loadAgremiacao(codigoAgremiacao));
        return this.repository.save(inscricao);

    }

    private Agremiacao loadAgremiacao(Long codigoAgremiacao) {
        return this.agremiacaoService.getAgremiacao(codigoAgremiacao);
    }
}
