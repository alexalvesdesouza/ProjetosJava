package br.com.lufamador.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.model.Jogo;
import br.com.lufamador.repository.ClassificacaoRepository;
import br.com.lufamador.utils.encripty.EncryptToMD5;

@Service
public class ClassificacaoService {

    private ClassificacaoRepository repository;

    @Autowired
    public ClassificacaoService(ClassificacaoRepository repository) {
        this.repository = repository;
    }

    public List<Classificacao> loadClassificacaoPorCategoria(String categoria, String chave) {
        return this.repository.listaClassificacoPorCriterio(categoria, chave);
    }

    private void registraPosicaoTabelaClassificacao(Classificacao classificacao) {
        this.repository.saveAndFlush(classificacao);
    }

    private final String geraKeyJogoUnico(Jogo jogo) throws NoSuchAlgorithmException {

        StringBuffer sb = new StringBuffer();

        sb.append(jogo.getAgremiacaoA()
                .getNome())
                .append(jogo.getAgremiacaoB()
                        .getNome())
                .append(jogo.getDataPartida()
                        .toString())
                .append(jogo.getChave());

        return EncryptToMD5.converterParaMD5(sb.toString());

    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void recalculaClassificacao(final Jogo jogo) {

        int golsEquipeA = jogo.getGolsAgremiacaoA();
        int golsEquipeB = jogo.getGolsAgremiacaoB();
        String fase = jogo.getFase();
        String chave = jogo.getChave();
        String categoria = jogo.getAgremiacaoA().getCategoria();

        Classificacao classificacaoAgremiacaoA = this.repository.findByAgremiacao_Codigo(jogo.getAgremiacaoA()
                .getCodigo());

        if (null == classificacaoAgremiacaoA.getChave()) {
            classificacaoAgremiacaoA.setChave(chave);
        }

        if (null == classificacaoAgremiacaoA.getCategoria()) {
            classificacaoAgremiacaoA.setCategoria(categoria);
        }

        if (null == classificacaoAgremiacaoA.getFase() || !fase.equals(classificacaoAgremiacaoA.getFase())) {
            classificacaoAgremiacaoA.setFase(fase);
        }

        classificacaoAgremiacaoA.setQtdJogos(classificacaoAgremiacaoA.getQtdJogos() - 1);
        classificacaoAgremiacaoA.setGolsPro(classificacaoAgremiacaoA.getGolsPro() - golsEquipeA);
        classificacaoAgremiacaoA.setGolsContra(classificacaoAgremiacaoA.getGolsContra() - golsEquipeB);

        Classificacao classificacaoAgremiacaoB = this.repository.findByAgremiacao_Codigo(jogo.getAgremiacaoB()
                .getCodigo());

        if (null == classificacaoAgremiacaoB.getChave()) {
            classificacaoAgremiacaoA.setChave(chave);
        }

        if (null == classificacaoAgremiacaoB.getCategoria()) {
            classificacaoAgremiacaoB.setCategoria(categoria);
        }

        if (null == classificacaoAgremiacaoB.getFase() || !fase.equals(classificacaoAgremiacaoB.getFase())) {
            classificacaoAgremiacaoB.setFase(fase);
        }

        classificacaoAgremiacaoB.setQtdJogos(classificacaoAgremiacaoB.getQtdJogos() - 1);
        classificacaoAgremiacaoB.setGolsPro(classificacaoAgremiacaoB.getGolsPro() - golsEquipeB);
        classificacaoAgremiacaoB.setGolsContra(classificacaoAgremiacaoB.getGolsContra() - golsEquipeA);

        if (golsEquipeA == golsEquipeB) {

            classificacaoAgremiacaoA.setQtdEmpates(
                    (classificacaoAgremiacaoA.getQtdEmpates() - 1) < 0 ? 0 : classificacaoAgremiacaoA.getQtdEmpates() - 1);
            classificacaoAgremiacaoA.setQtdPontos(classificacaoAgremiacaoA.getQtdPontos() - 1);

            classificacaoAgremiacaoB.setQtdEmpates(
                    (classificacaoAgremiacaoB.getQtdEmpates() - 1) < 0 ? 0 : classificacaoAgremiacaoB.getQtdEmpates() - 1);

            classificacaoAgremiacaoB.setQtdPontos(
                    (classificacaoAgremiacaoB.getQtdPontos() - 1) < 0 ? 0 : classificacaoAgremiacaoB.getQtdPontos() - 1);

        } else if (golsEquipeA > golsEquipeB) {

            classificacaoAgremiacaoA.setQtdPontos(
                    (classificacaoAgremiacaoA.getQtdPontos() - 3) < 0 ? 0 : classificacaoAgremiacaoA.getQtdPontos() - 3);

            classificacaoAgremiacaoA.setQtdVitorias(
                    (classificacaoAgremiacaoA.getQtdVitorias() - 1) < 0 ? 0 : classificacaoAgremiacaoA.getQtdVitorias() - 1);


        } else {

            classificacaoAgremiacaoB.setQtdPontos(
                    (classificacaoAgremiacaoB.getQtdPontos() - 3) < 0 ? 0 : classificacaoAgremiacaoB.getQtdPontos() - 3);

            classificacaoAgremiacaoB.setQtdVitorias(
                    (classificacaoAgremiacaoB.getQtdVitorias() - 1) < 0 ? 0 : classificacaoAgremiacaoB.getQtdVitorias() - 1);
        }

        this.repository.saveAndFlush(classificacaoAgremiacaoA);
        this.repository.saveAndFlush(classificacaoAgremiacaoB);
        this.geraClassificacao(categoria, chave);

    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void geraClassificacao(final Jogo jogo) {

        int golsEquipeA = jogo.getGolsAgremiacaoA();
        int golsEquipeB = jogo.getGolsAgremiacaoB();
        String fase = jogo.getFase();
        String chave = jogo.getChave();
        String categoria = jogo.getAgremiacaoA().getCategoria();

        Classificacao classificacaoAgremiacaoA = this.repository.findByAgremiacao_Codigo(jogo.getAgremiacaoA()
                .getCodigo());

        if (null == classificacaoAgremiacaoA.getChave()) {
            classificacaoAgremiacaoA.setChave(chave);
        }

        if (null == classificacaoAgremiacaoA.getCategoria()) {
            classificacaoAgremiacaoA.setCategoria(categoria);
        }

        if (null == classificacaoAgremiacaoA.getFase() || !fase.equals(classificacaoAgremiacaoA.getFase())) {
            classificacaoAgremiacaoA.setFase(fase);
        }

        classificacaoAgremiacaoA.setQtdJogos(classificacaoAgremiacaoA.getQtdJogos() + 1);
        classificacaoAgremiacaoA.setGolsPro(classificacaoAgremiacaoA.getGolsPro() + golsEquipeA);
        classificacaoAgremiacaoA.setGolsContra(classificacaoAgremiacaoA.getGolsContra() + golsEquipeB);

        Classificacao classificacaoAgremiacaoB = this.repository.findByAgremiacao_Codigo(jogo.getAgremiacaoB()
                .getCodigo());

        if (null == classificacaoAgremiacaoB.getChave()) {
            classificacaoAgremiacaoA.setChave(chave);
        }

        if (null == classificacaoAgremiacaoB.getCategoria()) {
            classificacaoAgremiacaoB.setCategoria(categoria);
        }

        if (null == classificacaoAgremiacaoB.getFase() || !fase.equals(classificacaoAgremiacaoB.getFase())) {
            classificacaoAgremiacaoB.setFase(fase);
        }

        classificacaoAgremiacaoB.setQtdJogos(classificacaoAgremiacaoB.getQtdJogos() + 1);
        classificacaoAgremiacaoB.setGolsPro(classificacaoAgremiacaoB.getGolsPro() + golsEquipeB);
        classificacaoAgremiacaoB.setGolsContra(classificacaoAgremiacaoB.getGolsContra() + golsEquipeA);

        if (golsEquipeA == golsEquipeB) {

            classificacaoAgremiacaoA.setQtdEmpates(classificacaoAgremiacaoA.getQtdEmpates() + 1);
            classificacaoAgremiacaoA.setQtdPontos(classificacaoAgremiacaoA.getQtdPontos() + 1);

            classificacaoAgremiacaoB.setQtdEmpates(classificacaoAgremiacaoB.getQtdEmpates() + 1);
            classificacaoAgremiacaoB.setQtdPontos(classificacaoAgremiacaoB.getQtdPontos() + 1);

        } else if (golsEquipeA > golsEquipeB) {

            classificacaoAgremiacaoA.setQtdPontos(classificacaoAgremiacaoA.getQtdPontos() + 3);
            classificacaoAgremiacaoA.setQtdVitorias(classificacaoAgremiacaoA.getQtdVitorias() + 1);

        } else {

            classificacaoAgremiacaoB.setQtdPontos(classificacaoAgremiacaoB.getQtdPontos() + 3);
            classificacaoAgremiacaoB.setQtdVitorias(classificacaoAgremiacaoB.getQtdVitorias() + 1);

        }

        this.repository.saveAndFlush(classificacaoAgremiacaoA);
        this.repository.saveAndFlush(classificacaoAgremiacaoB);
        this.geraClassificacao(categoria, chave);
    }


    private void geraClassificacao(String categoria, String chave) {
        List<Classificacao> classificacoes = this.repository.listaClassificacoPorCriterio(categoria, chave);

        int posicao = 1;

        for (Classificacao classificacao : classificacoes) {

            if (!chave.equals(classificacao.getChave())) {
                chave = classificacao.getChave();
                posicao = 1;
            }
            classificacao.setPosClassificacao(posicao);
            this.registraPosicaoTabelaClassificacao(classificacao);
            posicao++;
        }

    }

}
