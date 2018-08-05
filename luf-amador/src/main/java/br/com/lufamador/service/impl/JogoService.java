package br.com.lufamador.service.impl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Jogo;
import br.com.lufamador.repository.JogoRepository;
import br.com.lufamador.utils.encripty.EncryptToMD5;
import br.com.lufamador.validate.JogoValidate;

@Service
public class JogoService {

    private final JogoRepository repository;
    private final JogoValidate validate;
    private final ClassificacaoService classificacaoService;

    @Autowired
    public JogoService(JogoRepository repository, JogoValidate validate, ClassificacaoService classificacaoService) {
        this.repository = repository;
        this.validate = validate;
        this.classificacaoService = classificacaoService;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public List<Jogo> cadastraJogo(List<Jogo> jogos) {
//        jogo.setDataAtualizacao(LocalDateTime.now());
//        jogo.setDataCriacao(LocalDateTime.now());

        jogos.forEach(jogo -> {

            try {

                final String key = this.geraKeyJogoUnico(jogo);
                if(this.validate.validaJogoExistente(jogo)) {
                    return;
                }
                if (null == jogo.getGolsAgremiacaoA())
                    jogo.setGolsAgremiacaoA(0);

                if (null == jogo.getGolsAgremiacaoB())
                    jogo.setGolsAgremiacaoB(0);

                if (null == jogo.getPartidaEncerrada())
                    jogo.setPartidaEncerrada(false);
                jogo.setKeyConfronto(key);
                this.repository.saveAndFlush(jogo);
            } catch (Exception e) {

            }

        });

        return jogos;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo atualizarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
//        jogo.setDataAtualizacao(LocalDateTime.now());
        String keyJogo = this.geraKeyJogoUnico(jogo);
        jogo.setKeyConfronto(keyJogo);
        final Jogo jogoAtualizado = this.repository.saveAndFlush(jogo);
        return jogoAtualizado;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo encerrarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
//        jogo.setDataAtualizacao(LocalDateTime.now());
        String keyJogo = this.geraKeyJogoUnico(jogo);
        jogo.setKeyConfronto(keyJogo);
        jogo.setPartidaEncerrada(true);
        final Jogo jogoAtualizado = this.repository.saveAndFlush(jogo);
        this.classificacaoService.geraClassificacao(jogoAtualizado);
        return jogoAtualizado;
    }

    public final String geraKeyJogoUnico(Jogo jogo) throws NoSuchAlgorithmException {

        StringBuffer sb = new StringBuffer();

        sb.append(jogo.getAgremiacaoA()
                .getNome())
                .append(jogo.getAgremiacaoB()
                        .getNome())
                .append(jogo.getDataPartida()
                        .toString())
                .append(jogo.getRodada());

        return EncryptToMD5.converterParaMD5(sb.toString());

    }

    public List<Jogo> getJogosTempoReal() {
        List<Jogo> jogos = this.repository.getJogosParaTempoReal(LocalDate.now())
                .stream()
                .filter(jogo -> !jogo.getPartidaEncerrada())
                .collect(Collectors.toList());
        jogos.stream()
                .forEach(item -> {
                    item.setChave(item.getChave()
                            .replace("_", " "));
                    item.setLocal(item.getLocal()
                            .replace("_", " "));
                });

        return jogos;
    }

    public List<Jogo> getResultadosJogos() {
        List<Jogo> jogos = this.repository.findAll()
                .stream()
                .filter(jogo -> jogo.getPartidaEncerrada())
                .collect(Collectors.toList());
        jogos.stream()
                .forEach(item -> {
                    item.setChave(item.getChave()
                            .replaceAll("_", " "));
                });

        return jogos;
    }
}
