package br.com.lufamador.service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

  private final JogoRepository       repository;
  private final JogoValidate         validate;
  private final ClassificacaoService classificacaoService;

  @Autowired
  public JogoService(JogoRepository repository, JogoValidate validate, ClassificacaoService classificacaoService) {
    this.repository = repository;
    this.validate = validate;
    this.classificacaoService = classificacaoService;
  }

  @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
  public Jogo cadastraJogo(Jogo jogo) {
    jogo.setDataAtualizacao(LocalDateTime.now());
    jogo.setDataCriacao(LocalDateTime.now());
    Jogo jogoSaved = null;
    this.validate.validaJogoExistente(jogo);
    try {
      if (null == jogo.getGolsAgremiacaoA())
        jogo.setGolsAgremiacaoA(0);

      if (null == jogo.getGolsAgremiacaoB())
        jogo.setGolsAgremiacaoB(0);

      if (null == jogo.getPartidaEncerrada())
        jogo.setPartidaEncerrada(false);

      jogoSaved = this.repository.saveAndFlush(jogo);
    } catch (Exception e) {

    }
    return jogoSaved;
  }

  @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
  public Jogo atualizarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
    jogo.setDataAtualizacao(LocalDateTime.now());
    String keyJogo = this.geraKeyJogoUnico(jogo);
    jogo.setKeyConfronto(keyJogo);
    final Jogo jogoAtualizado = this.repository.saveAndFlush(jogo);
    // this.classificacaoService.geraClassificacao(jogoAtualizado);
    return jogoAtualizado;
  }

  // public void updateResultadoJogoTempoReal(final Jogo jogo)

  @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
  public Jogo encerrarJogo(final Jogo jogo) throws NoSuchAlgorithmException {
    jogo.setDataAtualizacao(LocalDateTime.now());
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
      .append(jogo.getChave())
      .append(jogo.getRodada());

    return EncryptToMD5.converterParaMD5(sb.toString());

  }

  @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
  public void cadastrarJogos(final List<Jogo> jogos) {
    jogos.forEach(jogo -> this.cadastraJogo(jogo));
  }

  public List<Jogo> getJogosTempoReal() {
    return this.repository.getJogosParaTempoReal(LocalDate.now())
                          .stream()
                          .filter(jogo -> !jogo.getPartidaEncerrada())
                          .collect(Collectors.toList());
    
  }
}
