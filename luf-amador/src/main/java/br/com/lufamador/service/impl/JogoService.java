package br.com.lufamador.service.impl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.model.Jogo;
import br.com.lufamador.model.LocalJogo;
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

    private Map<Long, String> loadChaves() {
        Map<Long, String> map = new HashMap<>();
        final List<Jogo> jogos = this.repository.findAll();
        jogos.forEach(jogo -> {
            if (null != jogo.getAgremiacaoA().getCodigo() && null != jogo.getChave()) {
                map.put(jogo.getAgremiacaoA().getCodigo(), jogo.getChave());
            }
            if (null != jogo.getAgremiacaoB().getCodigo() && null != jogo.getChave()) {
                map.put(jogo.getAgremiacaoB().getCodigo(), jogo.getChave());
            }
        });
        return map;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public List<Jogo> cadastraJogo(List<Jogo> jogos) {

        final Map<Long, String> map = this.loadChaves();

        jogos.forEach(jogo -> {

            try {

                if (null == jogo.getPartidaEncerrada()) {
                    jogo.setPartidaEncerrada(false);
                }

                if (jogo.getPartidaEncerrada()) {
                    return;
                }

                final String key = this.geraKeyJogoUnico(jogo);
                if (this.validate.validaJogoExistente(jogo)) {
                    return;
                }

                if (null == jogo.getCategoria()) {
                    String categoria = jogo.getAgremiacaoA().getCategoria();
                    if (null == categoria) {
                        categoria = jogo.getAgremiacaoB().getCategoria();
                    }
                    jogo.setCategoria(categoria);
                }

//                String chave = map.get(jogo.getAgremiacaoA().getCodigo());
//                if (null == chave) {
//                    chave = map.get(jogo.getAgremiacaoB().getCodigo());
//                }
//                jogo.setChave(chave);

                if (null == jogo.getGolsAgremiacaoA())
                    jogo.setGolsAgremiacaoA(0);

                if (null == jogo.getGolsAgremiacaoB())
                    jogo.setGolsAgremiacaoB(0);

                jogo.setKeyConfronto(key);
                jogo.setDataAtualizacao(LocalDateTime.now());
                jogo.setDataCriacao(LocalDateTime.now());
                this.repository.saveAndFlush(jogo);
            } catch (Exception e) {

            }

        });

        return jogos;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo atualizarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
        jogo.setDataAtualizacao(LocalDateTime.now());
        jogo.setKeyConfronto(this.geraKeyJogoUnico(jogo));
        return this.repository.saveAndFlush(jogo);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo editarJogoEncerrado(final Jogo jogo) throws NoSuchAlgorithmException {

        jogo.setDataAtualizacao(LocalDateTime.now());
        String keyJogo = this.geraKeyJogoUnico(jogo);
        jogo.setKeyConfronto(keyJogo);

        Optional<Jogo> antigo = this.repository.findById(jogo.getCodigo());
        recalculaClassificacao(antigo.get());

        Jogo saved = this.repository.saveAndFlush(jogo);
        this.classificacaoService.geraClassificacao(saved);

        return saved;
    }

    private void recalculaClassificacao(final Jogo jogo) {
        this.classificacaoService.recalculaClassificacao(jogo);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo encerrarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
        jogo.setDataAtualizacao(LocalDateTime.now());
        String keyJogo = this.geraKeyJogoUnico(jogo);
        jogo.setKeyConfronto(keyJogo);
        jogo.setPartidaEncerrada(true);

        Optional<Jogo> saved = this.repository.findById(jogo.getCodigo());
        if (saved.isPresent()) {
            if (saved.get().getPartidaEncerrada()) {
                throw new BussinessException("Partida encerrada");
            }
        }

        final Jogo jogoAtualizado = this.repository.saveAndFlush(jogo);
        this.classificacaoService.geraClassificacao(jogoAtualizado);
        return jogoAtualizado;
    }

    private String geraKeyJogoUnico(Jogo jogo) throws NoSuchAlgorithmException {

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

    public List<Jogo> jogosTempoRealPorCategoria(String categoria) {
        List<Jogo> jogos = this.getJogosTempoReal()
                .stream()
                .filter(jogo -> jogo.getAgremiacaoA().getCategoria().equals(categoria)
                        || jogo.getAgremiacaoB().getCategoria().equals(categoria))
                .collect(
                        Collectors.toList());

        jogos.stream().sorted(Comparator.comparing(Jogo::getDataAtualizacao).reversed()).collect(
                Collectors.toList());

        jogos.forEach(item -> {
            item.setDataCriacao(null);
            item.setDataAtualizacao(null);
        });

        return jogos;
    }

    public List<Jogo> getJogosEditList(String categoria, String chave, String dataRodada) {
        LocalDate data;
        List<Jogo> jogos;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            data = LocalDate.parse(dataRodada, formatter);

            jogos = this.repository.getJogosEditList(data, data, chave);
        } catch (Exception e) {
            data = LocalDate.now().plusDays(-7);
            jogos = this.repository.getJogosEditList(data, LocalDate.now(), chave);
        }

        if (null == jogos)
            return new ArrayList<>();

        List<Jogo> collect = jogos.stream()
                .filter(jogo -> jogo.getAgremiacaoA().getCategoria().equals(categoria))
                .filter(jogo -> jogo.getAgremiacaoB().getCategoria().equals(categoria))
                .collect(Collectors.toList());

        collect.forEach(jogo -> {
            jogo.setDataCriacao(null);
            jogo.setDataAtualizacao(null);
        });

        return collect;
    }

    private List<Jogo> getJogosTempoReal() {
        return this.repository.getJogosParaTempoReal(LocalDate.now());

    }

    public List<Jogo> getResultadosJogos(String categoria) {
        List<Jogo> jogos = this.repository.findAll()
                .stream()
                .filter(jogo -> jogo.getPartidaEncerrada())
                .collect(Collectors.toList());
        return jogos.stream().filter(jogo -> jogo.getAgremiacaoA().getCategoria().equals(categoria)
                || jogo.getAgremiacaoB().getCategoria().equals(categoria)).collect(Collectors.toList());
    }

    public List<String> getDatasPartidas() {
        return this.repository.getDatasPartidas();
    }
}
