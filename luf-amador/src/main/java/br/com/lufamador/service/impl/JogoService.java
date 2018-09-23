package br.com.lufamador.service.impl;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import br.com.lufamador.repository.JogoRepository;
import br.com.lufamador.utils.encripty.EncryptToMD5;
import br.com.lufamador.validate.JogoValidate;

@Service
public class JogoService {

    private final JogoRepository repository;
    private final JogoValidate validate;
    private final ClassificacaoService classificacaoService;
    private final List<String> keysConfrontos;
    private final Map<Long, String> mapChaves;
    private final List<Jogo> allJogos;

    @Autowired
    public JogoService(JogoRepository repository, JogoValidate validate, ClassificacaoService classificacaoService) {
        this.repository = repository;
        this.validate = validate;
        this.classificacaoService = classificacaoService;
        this.allJogos = this.repository.findAll();
        this.mapChaves = this.loadChaves();
        this.keysConfrontos = this.loadKeysConfrontos();
    }

    private Map<Long, String> loadChaves() {
        Map<Long, String> map = new HashMap<>();

        this.allJogos.forEach(jogo -> {
            if (null != jogo.getAgremiacaoA().getCodigo() && null != jogo.getChave()) {
                map.put(jogo.getAgremiacaoA().getCodigo(), jogo.getChave());
            }
            if (null != jogo.getAgremiacaoB().getCodigo() && null != jogo.getChave()) {
                map.put(jogo.getAgremiacaoB().getCodigo(), jogo.getChave());
            }
        });
        return map;
    }

    private List<String> loadKeysConfrontos() {
        return this.allJogos.stream().map(Jogo::getKeyConfronto).collect(Collectors.toList());
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public List<Jogo> cadastraJogo(List<Jogo> jogos) {

        jogos.forEach(jogo -> {

            try {

                if (null == jogo.getPartidaEncerrada()) {
                    jogo.setPartidaEncerrada(false);
                }

                if (jogo.getPartidaEncerrada()) {
                    return;
                }

                String keyConfronto = this.geraKeyJogoUnico(jogo);
                if (this.keysConfrontos.contains(keyConfronto)) {
//                    throw new BussinessException(
//                            "Jogo entre: " + jogo.getAgremiacaoA().getNome() + " e "
//                                    + jogo.getAgremiacaoB().getNome() + " já existente na fase: " + jogo.getFase());
                    return;
                }

                if (null == jogo.getCategoria()) {
                    String categoria = jogo.getAgremiacaoA().getCategoria();
                    if (null == categoria) {
                        categoria = jogo.getAgremiacaoB().getCategoria();
                    }
                    jogo.setCategoria(categoria);
                }

                String chaveA = mapChaves.get(jogo.getAgremiacaoA().getCodigo());
                String chaveB = mapChaves.get(jogo.getAgremiacaoB().getCodigo());

                if (null == chaveA && null == chaveB && null == jogo.getChave()) {
                    throw new BussinessException("Informe a chave do jogo");
                }

                if (null == chaveA && null != chaveB) {
                    chaveA = chaveB;
                } else if (null == chaveB && null != chaveA) {
                    chaveB = chaveA;
                }

                if (!chaveA.equals(chaveB)) {
                    throw new BussinessException("Agremiações com chaves divergentes");
                }

                if (null == jogo.getChave() || jogo.getChave().equals("")) {
                    jogo.setChave(chaveA);
                }

                if (null == jogo.getGolsAgremiacaoA()) {
                    jogo.setGolsAgremiacaoA(0);
                }

                if (null == jogo.getGolsAgremiacaoB()) {
                    jogo.setGolsAgremiacaoB(0);
                }

                jogo.setKeyConfronto(keyConfronto);
                jogo.setDataAtualizacao(LocalDateTime.now());
                jogo.setDataCriacao(LocalDateTime.now());
                this.repository.saveAndFlush(jogo);
            } catch (Exception e) {
                throw new BussinessException(e.getMessage());
            }

        });

        return jogos;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo atualizarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
        if (jogo.getDataPartida().isAfter(LocalDate.now())) {
            throw new BussinessException("Partida não pode ser atualizada pois o jogo ainda não esta em andamento");
        }
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
        if (antigo.isPresent()) {
            recalculaClassificacao(antigo.get());
        }

        Jogo saved = this.repository.saveAndFlush(jogo);
        this.classificacaoService.geraClassificacao(saved);

        return saved;
    }

    private void recalculaClassificacao(final Jogo jogo) throws NoSuchAlgorithmException {
        this.classificacaoService.recalculaClassificacao(jogo);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Jogo encerrarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
        jogo.setDataAtualizacao(LocalDateTime.now());
        String keyJogo = this.geraKeyJogoUnico(jogo);
        jogo.setKeyConfronto(keyJogo);
        jogo.setPartidaEncerrada(true);

        Optional<Jogo> saved = this.repository.findById(jogo.getCodigo());
        if (saved.isPresent() && saved.get().getPartidaEncerrada()) {
            throw new BussinessException("Partida encerrada");
        }

        if (jogo.getDataPartida().isAfter(LocalDate.now())) {
            throw new BussinessException("Partida não pode ser encerrada pois ainda não aconteceu.");
        }

        final Jogo jogoAtualizado = this.repository.saveAndFlush(jogo);
        this.classificacaoService.geraClassificacao(jogoAtualizado);
        return jogoAtualizado;
    }

    private String geraKeyJogoUnico(Jogo jogo) throws NoSuchAlgorithmException {

        StringBuilder sb = new StringBuilder();

        Agremiacao agremiacaoA = jogo.getAgremiacaoA();
        Agremiacao agremiacaoB = jogo.getAgremiacaoB();
        final Integer codigoCompeticao = jogo.getCodigoCompeticao();
        String fase = jogo.getFase();

        sb.append(agremiacaoA.getCodigo())
                .append(agremiacaoB.getCodigo())
                .append(codigoCompeticao)
                .append(fase);

        return EncryptToMD5.converterParaMD5(sb.toString());

    }

    public List<Jogo> jogosTempoRealPorCategoria(String categoria) {
        List<Jogo> jogos = this.getJogosTempoReal(categoria);

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

    private List<Jogo> getJogosTempoReal(String categoria) {
        return this.repository.getJogosParaTempoReal(categoria);
    }

    public List<String> getDatasPartidas() {
        return this.repository.getDatasPartidas();
    }

    @Transactional
    public void delete(Long codigo) {
        final Jogo jogo = this.getJogo(codigo);
        if (jogo.getPartidaEncerrada()) {
            throw new BussinessException("Jogo não pode ser deletado, pois ja esta encerrado");
        }
        this.repository.delete(jogo);
    }

    private Jogo getJogo(Long codigo) {
        this.repository.deleteFromLufTabelaJogos(codigo);
        return this.repository.findByCodigo(codigo);
    }
}
