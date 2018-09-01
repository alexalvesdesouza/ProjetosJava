package br.com.lufamador.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.lufamador.exception.ValidateException;
import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.repository.AgremiacaoRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;

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
        this.validaSeDataDeAfiliacaoNaoEstaVazia(agremiacao);
    }

    public void validaAtualizacaoAgremiacao(final Agremiacao agremiacao) {
        this.validaSeDataDeAfiliacaoNaoEstaVazia(agremiacao);
    }

    private void validaSeDataDeAfiliacaoNaoEstaVazia(Agremiacao agremiacao) {
        if (null == agremiacao.getDataAfiliacao())
            throw new ValidateException(MensagensErro.DATA_AFILIACAO_VAZIA);
    }

    public void validaAgremiacaoExistente(final Agremiacao agremiacao) {
        Agremiacao agremiacaoValidate = this.repository.findByNomeAndCategoria(agremiacao.getNome(), agremiacao.getCategoria());
        if (agremiacaoValidate != null)
            throw new ValidateException(MensagensErro.ENTIDADE_DUPLICADA.replace("?", "Agremiação"));
    }

    public void validaSeAtletasAgremiacaoJaNaoEstaoInscritosEmOutraAgremiacao(final Agremiacao agremiacao) {
    }

    public void deletarAgremiacao(Agremiacao agremiacao) {
        if (0 != this.repository.localizaInscricaoDeAgremiacao(agremiacao.getCodigo()))
            throw new ValidateException(
                    MensagensErro.ERRO_DELETE_AGREMIACAO_INSCRITA.replace("?", agremiacao.getNome()));
    }
}
