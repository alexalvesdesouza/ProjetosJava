package br.com.lufamador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Inscricao;
import br.com.lufamador.repository.InscricaoRepository;
import br.com.lufamador.validate.InscricaoValidate;

@Service
public class InscricaoService {

    private final InscricaoRepository repository;
    private final InscricaoValidate validate;
    private final CampeonatoService campeonatoService;

    @Autowired
    public InscricaoService(InscricaoRepository repository, InscricaoValidate validate, CampeonatoService
            campeonatoService) {
        this.repository = repository;
        this.validate = validate;
        this.campeonatoService = campeonatoService;
    }

//    public Inscricao cadastraInscricao(Inscricao inscricao) {
//        Inscricao inscricaoSaved = null;
//        //this.validate.validaInscricaoExistente(inscricao);
//        try {
//            inscricaoSaved = this.repository.saveAndFlush(inscricao);
//            this.campeonatoService.insereInscritos(inscricaoSaved);
//        } catch (Exception e) {
//
//        }
//        return inscricaoSaved;
//    }

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
}
