package br.com.lufamador.validate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exception.ValidateException;
import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.repository.AgremiacaoRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;
import sun.security.validator.ValidatorException;

@Component
public class AgremiacaoValidate {

    private final AgremiacaoRepository repository;

    @Autowired
    public AgremiacaoValidate(AgremiacaoRepository repository) {
        this.repository = repository;
    }

    public void validaCadastroAgremiacao(final Agremiacao agremiacao) {
        this.validaAgremiacaoExistente(agremiacao);
        this.validaSeAtletasAgremiacaoJaNaoEstaoInscritosEmOutraAgremiacao(agremiacao);
    }

    public void validaAgremiacaoExistente(final Agremiacao agremiacao) {
        Agremiacao agremiacaoValidate =  this.repository.findByNomeSigla(agremiacao.getNomeSigla());
        if (agremiacaoValidate != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Agremiação"));
    }

    public void validaSeAtletasAgremiacaoJaNaoEstaoInscritosEmOutraAgremiacao(final Agremiacao agremiacao) {
//        List<Agremiacao> agremiacaoList = this.repository.findAll();
//        if (agremiacaoList.isEmpty())
//            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Agremiação"));
    }

   }
