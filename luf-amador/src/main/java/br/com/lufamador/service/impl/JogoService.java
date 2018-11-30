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

import org.apache.commons.lang.StringUtils;
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
    private final Map<String, String> mapChaves;
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

    private Map<String, String> loadChaves() {
        Map<String, String> map = new HashMap<>();

        this.allJogos.forEach(jogo -> {
            if (null != jogo.getAgremiacaoA().getCodigo() && null != jogo.getChave()) {
                String key = jogo.getAgremiacaoA().getCodigo().toString().concat(jogo.getFase());
                map.put(key, jogo.getChave());
            }
            if (null != jogo.getAgremiacaoB().getCodigo() && null != jogo.getChave()) {
                String key = jogo.getAgremiacaoB().getCodigo().toString().concat(jogo.getFase());
                map.put(key, jogo.getChave());
            }
        });
        return map;
    }

    private List<String> loadKeysConfrontos() {
        return this.allJogos.stream().map(Jogo::getKeyConfronto).collect(Collectors.toList());
    }

    @Transactional
    public List<Jogo> cadastraJogo(List<Jogo> jogos) {

        jogos.forEach(jogo -> {

            try {

                if (null == jogo.getPartidaEncerrada()) {
                    jogo.setPartidaEncerrada(false);
                }

                if (jogo.getPartidaEncerrada()) {
                    return;
                }
                String fase = jogo.getFase();
                if (fase.equals("2") && StringUtils.isBlank(jogo.getTurno())) {
                    String turno = "ida";
                    if (jogo.getRodada().equals("2")) {
                        turno = "volta";
                    }
                    jogo.setTurno(turno);
                }
                String keyConfronto = this.geraKeyJogoUnico(jogo);
                if (this.keysConfrontos.contains(keyConfronto)) {
//                    throw new BussinessException(
//                            "Jogo entre: " + jogo.getAgremiacaoA().getNome() + " e "
//                                    + jogo.getAgremiacaoB().getNome() + " já existente na fase: " + jogo.getFase());
                    return;
                }

                if (StringUtils.isBlank(jogo.getCategoria())) {
                    String categoria = jogo.getAgremiacaoA().getCategoria();
                    if (StringUtils.isBlank(categoria)) {
                        categoria = jogo.getAgremiacaoB().getCategoria();
                    }
                    jogo.setCategoria(categoria);
                }

                String key = jogo.getAgremiacaoA().getCodigo().toString().concat(jogo.getFase());
                String chaveA = mapChaves.get(key);
                key = jogo.getAgremiacaoB().getCodigo().toString().concat(jogo.getFase());
                String chaveB = mapChaves.get(key);

                if (StringUtils.isBlank(chaveA)  &&  StringUtils.isBlank(chaveB) && StringUtils.isBlank(jogo.getChave())) {
                    throw new BussinessException("Informe a chave do jogo");
                }

                if (StringUtils.isBlank(chaveA) && StringUtils.isBlank(chaveB) && !StringUtils.isBlank(jogo.getChave())) {
                    chaveA = jogo.getChave();
                    chaveB = chaveA;
                }

                if (StringUtils.isBlank(chaveA) && !StringUtils.isBlank(chaveB)) {
                    chaveA = chaveB;
                } else if (StringUtils.isBlank(chaveB) && !StringUtils.isBlank(chaveA)) {
                    chaveB = chaveA;
                }

                if (!chaveA.equals(chaveB)) {
                    throw new BussinessException("Agremiações com chaves divergentes");
                }

                if (StringUtils.isBlank(jogo.getChave())) {
                    jogo.setChave(chaveA);
                }

                if (null == jogo.getGolsAgremiacaoA()) {
                    jogo.setGolsAgremiacaoA(0);
                }

                if (null == jogo.getGolsAgremiacaoB()) {
                    jogo.setGolsAgremiacaoB(0);
                }

                if (null == jogo.getGolsPenaltisAgremiacaoA()) {
                    jogo.setGolsPenaltisAgremiacaoA(0);
                }

                if (null == jogo.getGolsPenaltisAgremiacaoB()) {
                    jogo.setGolsPenaltisAgremiacaoB(0);
                }

                jogo.setKeyConfronto(keyConfronto);
                jogo.setDataAtualizacao(LocalDateTime.now());
                jogo.setDataCriacao(LocalDateTime.now());
                this.repository.save(jogo);
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

        if (jogo.iswAgremiacaoA() || jogo.iswAgremiacaoB()) {
            jogo.setGolsAgremiacaoA(0);
            jogo.setGolsAgremiacaoB(0);
        }

        if (jogo.isPenaltis()) {
            System.out.println(777);
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
        String turno = jogo.getTurno();
        if (null == turno) {
            turno = fase;
        }

        sb.append(agremiacaoA.getCodigo())
                .append(agremiacaoB.getCodigo())
                .append(codigoCompeticao)
                .append(fase)
                .append(turno);

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

    public List<Jogo> getAllJogosPorAgremiacao(Long codigoAgremiacao, Long codigoCampeonato) {
        return this.repository.findByAgremiacaoAndCampeonato(codigoAgremiacao, codigoCampeonato);
    }

    public List<Jogo> getJogosEditList(String categoria, String chave, String fase, String dataRodada) {
        LocalDate data;
        List<Jogo> jogos;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            data = LocalDate.parse(dataRodada, formatter);

            jogos = this.repository.getJogosEditList(data, data, chave, fase);
        } catch (Exception e) {
            data = LocalDate.now().plusDays(-7);
            jogos = this.repository.getJogosEditList(data, LocalDate.now(), chave, fase);
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
