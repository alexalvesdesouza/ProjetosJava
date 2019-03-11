package br.com.lufamador.service.impl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.repository.ClassificacaoRepository;
import br.com.lufamador.utils.encripty.EncryptToMD5;

@Service
public class ClassificacaoService {

    private final Logger logger = LoggerFactory.getLogger(ClassificacaoService.class);
    private final long SEGUNDO = 1000;
    private final long MINUTO = SEGUNDO * 60;
    private final long HORA = MINUTO * 60;

    @Autowired
    private ClassificacaoRepository repository;

//    @Autowired
//    private JavaMailUtil javaMailUtil;

    @Autowired
    private IntervencaoServiceImpl intervencaoService;

    @Autowired
    private ClassificacaoHistoryService historyService;

    public ClassificacaoService() {

    }

    public List<Classificacao> loadClassificacaoPorCategoria(String categoria, String chave, String fase) {
        return this.repository.listaClassificacoPorCriterio(categoria, chave, fase);
    }

    private void registraPosicaoTabelaClassificacao(Classificacao classificacao) {
        this.repository.saveAndFlush(classificacao);
    }

    private String geraKeyClassificacao(Long codigoAgremiacao, Integer codigoCampeonato,
                                        String fase) throws NoSuchAlgorithmException {

        StringBuilder sb = new StringBuilder();

        sb.append(codigoAgremiacao)
                .append(codigoCampeonato)
                .append(fase);

        return EncryptToMD5.converterParaMD5(sb.toString());

    }

    public void loadClassificacoesDuplicadas() {

        Map<String, Classificacao> map = new HashMap<>();
        this.repository.findAll().forEach(classificacao -> {

            StringBuilder sb = new StringBuilder();
            sb.append(classificacao.getFase())
                    .append("_").append(classificacao.getChave())
                    .append("_").append(classificacao.getCategoria());

            map.put(sb.toString(), classificacao);
        });

        List<Integer> classificacoes = new ArrayList<>();

        map.values().forEach(classificacao -> {
            Integer code = this.repository.loadClassificacoesDuplicadas(classificacao.getFase(),
                    classificacao.getChave(), classificacao.getCategoria());
            if (null != code) {
                classificacoes.add(code);
            }
        });

        this.sendEmailClassificacaoDuplicada(classificacoes);
    }

    private void sendEmailClassificacaoDuplicada(List<Integer> classificacoes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Classificações duplicacas\n");
        if (!classificacoes.isEmpty()) {
            classificacoes.forEach(item -> {
                sb.append("[ ").append(item).append(" ]");

            });

//            javaMailUtil.sendEmail(sb.toString(), "Classificações duplidadas");
        }


    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void recalculaClassificacao(final Jogo jogo) throws NoSuchAlgorithmException {

        int golsEquipeA = jogo.getGolsAgremiacaoA();
        int golsEquipeB = jogo.getGolsAgremiacaoB();
        boolean wAgremiacaoA = jogo.iswAgremiacaoA();
        boolean wAgremiacaoB = jogo.iswAgremiacaoB();
        String fase = jogo.getFase();
        String chave = jogo.getChave();
        String categoria = jogo.getAgremiacaoA().getCategoria();
        if (null == categoria) {
            categoria = jogo.getAgremiacaoB().getCategoria();
        }

        final String keyA = this.geraKeyClassificacao(jogo.getAgremiacaoA().getCodigo(), jogo.getCodigoCompeticao(),
                fase);
        Classificacao classificacaoAgremiacaoA = this.repository.findByAgremiacaoAndKeyMD5(jogo.getAgremiacaoA(), keyA);

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
        classificacaoAgremiacaoA.setKeyMD5(keyA);

        final String keyB = this.geraKeyClassificacao(jogo.getAgremiacaoB().getCodigo(), jogo.getCodigoCompeticao(),
                fase);
        Classificacao classificacaoAgremiacaoB = this.repository.findByAgremiacaoAndKeyMD5(jogo.getAgremiacaoB(), keyB);

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
        classificacaoAgremiacaoB.setKeyMD5(keyB);

        boolean isWO = false;
        if (wAgremiacaoA || wAgremiacaoB) {
            isWO = true;
        }

        if (golsEquipeA == golsEquipeB && !isWO) {

            classificacaoAgremiacaoA.setQtdEmpates(
                    (classificacaoAgremiacaoA.getQtdEmpates() - 1) < 0 ? 0 : classificacaoAgremiacaoA.getQtdEmpates() - 1);
            classificacaoAgremiacaoA.setQtdPontos(classificacaoAgremiacaoA.getQtdPontos() - 1);

            classificacaoAgremiacaoB.setQtdEmpates(
                    (classificacaoAgremiacaoB.getQtdEmpates() - 1) < 0 ? 0 : classificacaoAgremiacaoB.getQtdEmpates() - 1);

            classificacaoAgremiacaoB.setQtdPontos(
                    (classificacaoAgremiacaoB.getQtdPontos() - 1) < 0 ? 0 : classificacaoAgremiacaoB.getQtdPontos() - 1);

        } else if (golsEquipeA > golsEquipeB || wAgremiacaoA) {

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

        if (null == classificacaoAgremiacaoA.getTemporada()) {
            classificacaoAgremiacaoA.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }
        if (null == classificacaoAgremiacaoB.getTemporada()) {
            classificacaoAgremiacaoB.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }

        this.repository.saveAndFlush(classificacaoAgremiacaoA);
        this.repository.saveAndFlush(classificacaoAgremiacaoB);
        this.geraClassificacao(categoria, chave, fase, jogo);

    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void geraClassificacao(final Jogo jogo) throws NoSuchAlgorithmException {

        int golsEquipeA = jogo.getGolsAgremiacaoA();
        int golsEquipeB = jogo.getGolsAgremiacaoB();
        boolean wAgremiacaoA = jogo.iswAgremiacaoA();
        boolean wAgremiacaoB = jogo.iswAgremiacaoB();
        boolean isPenalts = jogo.isPenaltis();
        String fase = jogo.getFase();
        String chave = jogo.getChave();
        String categoria = jogo.getAgremiacaoA().getCategoria();

        if (isPenalts) {
            golsEquipeA = jogo.getGolsPenaltisAgremiacaoA();
            golsEquipeB = jogo.getGolsPenaltisAgremiacaoB();
        }

        if (null == categoria) {
            categoria = jogo.getAgremiacaoB().getCategoria();
        }
        int codigoCampeonato = jogo.getCodigoCompeticao();

        final String keyA = this.geraKeyClassificacao(jogo.getAgremiacaoA().getCodigo(), jogo.getCodigoCompeticao(),
                fase);

        Classificacao classificacaoAgremiacaoA = this.repository.findByKeyMD5AndCampeonatoCodigo(keyA,
                codigoCampeonato);

        if (null == classificacaoAgremiacaoA) {
            classificacaoAgremiacaoA = new Classificacao(jogo.getAgremiacaoA(), codigoCampeonato, 0, 0, 0, 0, 0, 0, 0,
                    chave, keyA, false, fase, categoria);
        }

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
        classificacaoAgremiacaoA.setKeyMD5(keyA);

        final String keyB = this.geraKeyClassificacao(jogo.getAgremiacaoB().getCodigo(), jogo.getCodigoCompeticao(),
                fase);
        Classificacao classificacaoAgremiacaoB = this.repository.findByKeyMD5AndCampeonatoCodigo(keyB,
                codigoCampeonato);

        if (null == classificacaoAgremiacaoB) {
            classificacaoAgremiacaoB = new Classificacao(jogo.getAgremiacaoB(), codigoCampeonato, 0, 0, 0, 0, 0, 0, 0,
                    chave, keyB, false, fase, categoria);
        }


        if (null == classificacaoAgremiacaoB.getChave()) {
            classificacaoAgremiacaoB.setChave(chave);
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
        classificacaoAgremiacaoB.setKeyMD5(keyB);

        if (wAgremiacaoA) {
            classificacaoAgremiacaoA.setQtdPontos(classificacaoAgremiacaoA.getQtdPontos() + 3);
            classificacaoAgremiacaoA.setQtdVitorias(classificacaoAgremiacaoA.getQtdVitorias() + 1);

        } else if (wAgremiacaoB) {
            classificacaoAgremiacaoB.setQtdPontos(classificacaoAgremiacaoB.getQtdPontos() + 3);
            classificacaoAgremiacaoB.setQtdVitorias(classificacaoAgremiacaoB.getQtdVitorias() + 1);
        }

        if (!wAgremiacaoA && !wAgremiacaoB) {

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
        }

        if (null == classificacaoAgremiacaoA.getTemporada()) {
            classificacaoAgremiacaoA.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }
        if (null == classificacaoAgremiacaoB.getTemporada()) {
            classificacaoAgremiacaoB.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }

        this.repository.saveAndFlush(classificacaoAgremiacaoA);
        this.repository.saveAndFlush(classificacaoAgremiacaoB);
        this.geraClassificacao(categoria, chave, fase, jogo);
    }

    private void geraClassificacao(String categoria, String chave, String fase, Jogo jogo) {

        List<Classificacao> classificacoes;

        if (fase.equalsIgnoreCase("QUARTAS")) {
            
        }

        if (fase.equals("2") && this.getCategorias().contains(categoria) && null != jogo) {
            Long codAgremiacaoA = jogo.getAgremiacaoA().getCodigo();
            Long codAgremiacaoB = jogo.getAgremiacaoB().getCodigo();

            classificacoes = this.repository.listaClassificacoPorCriterioFase2(categoria, chave, fase, codAgremiacaoA,
                    codAgremiacaoB);

        } else {
            classificacoes = this.repository.listaClassificacoPorCriterio(categoria, chave, fase);
        }


        int posicao = 1;

        if (null != classificacoes) {

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

    private List<String> getCategorias() {
        return Arrays.asList("AMADOR_ACESSO", "AMADOR_ESPECIAL");
    }

    public List<Classificacao> finalizaClassificacaoPorFase(List<Classificacao> classificacoes) {
        classificacoes.forEach(classificacao -> this.repository.saveAndFlush(classificacao));
        return classificacoes;
    }

    public Classificacao classificaAgremiacao(Classificacao classificacao) {
        return this.repository.saveAndFlush(classificacao);
    }

    public Classificacao editaResultadoClassificacao(Classificacao classificacao) {

        Classificacao anterior = this.getClassificacao(classificacao.getCodigo());

        this.insereHistorico(anterior);

        int pontosCorrigidos = classificacao.getQtdPontos();
        int pontosAntigos = anterior.getQtdPontos();

        this.intervencaoService.createOrUpdate(classificacao.getIntervencoes(), pontosCorrigidos, pontosAntigos);
        classificacao = this.repository.saveAndFlush(classificacao);
        this.geraClassificacao(classificacao.getCategoria(), classificacao.getChave(), classificacao.getFase(), null);
        return classificacao;
    }

    private void insereHistorico(Classificacao classificacao) {
        this.historyService.insereHistorico(classificacao);
    }

    private Classificacao getClassificacao(Long codigo) {
        return this.repository.findById(codigo).get();
    }

}
