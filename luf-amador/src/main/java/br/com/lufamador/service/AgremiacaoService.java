package br.com.lufamador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.repository.AgremiacaoRepository;
import br.com.lufamador.validate.AgremiacaoValidate;

@Service
public class AgremiacaoService {

    private final AgremiacaoRepository repository;
    private final AgremiacaoValidate validate;
    private final EnderecoService enderecoService;

    @Autowired
    public AgremiacaoService(AgremiacaoRepository repository, AgremiacaoValidate validate, EnderecoService enderecoService) {
        this.repository = repository;
        this.validate = validate;
        this.enderecoService = enderecoService;
    }

    public Agremiacao cadastraAgremiacao(Agremiacao agremiacao) {
        Agremiacao agremiacaoSaved = null;
        agremiacao.setNomeSigla(this.getNomeSigla(agremiacao.getNome()));

        this.validate.validaCadastroAgremiacao(agremiacao);
        try {
            this.enderecoService.cadastraEndereco(agremiacao.getEndereco());
            agremiacaoSaved = this.repository.saveAndFlush(agremiacao);
        } catch (Exception e) {
        }
        return agremiacaoSaved;
    }

    public final String getNomeSigla(String sigla) {

        String siglaRetorno = sigla.toLowerCase();
        siglaRetorno = siglaRetorno.replace(" ", "_")
                .replace("ç", "c")
                .replace("á", "a").replace("à", "a").replace("â", "a").replace("ã", "a")
                .replace("é", "e").replace("è", "e").replace("ê", "e").replace("ẽ", "e")
                .replace("í", "i").replace("ì", "i").replace("î", "i").replace("ĩ", "i")
                .replace("ó", "o").replace("ò", "o").replace("ô", "o").replace("õ", "o")
                .replace("ú", "u").replace("ù", "u").replace("û", "u").replace("ũ", "u");

        return siglaRetorno.toUpperCase();
    }

    public List<Agremiacao> getAgremiacoes() {
        return this.repository.findAll();
    }

    public final Agremiacao atulizarAgremiacao(Agremiacao agremiacao) {
        return this.repository.saveAndFlush(agremiacao);
    }
}
