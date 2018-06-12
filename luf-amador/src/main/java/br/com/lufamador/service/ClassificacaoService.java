package br.com.lufamador.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Classificacao;
import br.com.lufamador.model.Jogo;
import br.com.lufamador.repository.ClassificacaoRepository;
import br.com.lufamador.utils.encripty.EncryptToMD5;

@Service
public class ClassificacaoService {

  private ClassificacaoRepository repository;
  private Boolean                 foiEmpate   = false;
  private Integer                 golsEquipeA = 0;
  private Integer                 golsEquipeB = 0;
  private String                  key         = null;

  @Autowired
  public ClassificacaoService(ClassificacaoRepository repository) {
    this.repository = repository;
  }

  public List<Classificacao> getClassificacoes() {
    return this.repository.listaClassificacoPorCriterio();
  }

  private final String geraKeyJogoUnico(Jogo jogo) throws NoSuchAlgorithmException {

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

  public void geraClassificacao(final Jogo jogo) throws NoSuchAlgorithmException {

    this.golsEquipeA = jogo.getGolsAgremiacaoA();
    this.golsEquipeB = jogo.getGolsAgremiacaoB();

    this.validaSeFoiEmpate();

    key = this.geraKeyJogoUnico(jogo);

    this.geraClassificacaoEquipeA(jogo);
    this.geraClassificacaoEquipeB(jogo);
    this.foiEmpate = false;
  }

  private void validaSeFoiEmpate() {
    if (this.golsEquipeA.intValue() == this.golsEquipeB.intValue()) {
      this.foiEmpate = true;
    }
  }

  private void geraClassificacaoEquipeA(final Jogo jogo) throws NoSuchAlgorithmException {

    Classificacao classificacaoAgremiacaoA = this.repository.findByAgremiacao_Codigo(jogo.getAgremiacaoA()
                                                                                         .getCodigo());   

    if (classificacaoAgremiacaoA != null) {

      Integer qtdJogos = classificacaoAgremiacaoA.getQtdJogos();
      Integer qtdPontos = classificacaoAgremiacaoA.getQtdPontos();
      Integer qtdVitorias = classificacaoAgremiacaoA.getQtdVitorias();
      Integer golsPro = classificacaoAgremiacaoA.getGolsPro();
      Integer golsContra = classificacaoAgremiacaoA.getGolsContra();

      classificacaoAgremiacaoA.setGolsPro(golsPro + this.golsEquipeA);
      classificacaoAgremiacaoA.setGolsContra(golsContra + this.golsEquipeB);

      if (!key.equals(jogo.getKeyConfronto())) {
        qtdJogos++;
        classificacaoAgremiacaoA.setQtdJogos(qtdJogos);
      }

      if (this.foiEmpate) {

        qtdPontos++;
        classificacaoAgremiacaoA.setQtdPontos(qtdPontos);
      } else if (this.golsEquipeA > this.golsEquipeB) {
        classificacaoAgremiacaoA.setQtdPontos(qtdPontos + 3);
        qtdVitorias++;
        classificacaoAgremiacaoA.setQtdVitorias(qtdVitorias);
      }

      classificacaoAgremiacaoA.setChave(jogo.getChave());
      this.repository.saveAndFlush(classificacaoAgremiacaoA);
    } else {
      this.insereClassificacaoEquipeA(jogo);
    }
  }

  private void geraClassificacaoEquipeB(final Jogo jogo) {

    Classificacao classificacaoAgremiacaoB = this.repository.findByAgremiacao_Codigo(jogo.getAgremiacaoB()
                                                                                         .getCodigo());
    if (classificacaoAgremiacaoB != null) {

      Integer qtdJogos = classificacaoAgremiacaoB.getQtdJogos();
      Integer qtdPontos = classificacaoAgremiacaoB.getQtdPontos();
      Integer qtdVitorias = classificacaoAgremiacaoB.getQtdVitorias();
      Integer golsPro = classificacaoAgremiacaoB.getGolsPro();
      Integer golsContra = classificacaoAgremiacaoB.getGolsContra();

      classificacaoAgremiacaoB.setGolsPro(golsPro + this.golsEquipeB);
      classificacaoAgremiacaoB.setGolsContra(golsContra + this.golsEquipeA);
      if (!key.equals(jogo.getKeyConfronto())) {
        qtdJogos++;
        classificacaoAgremiacaoB.setQtdJogos(qtdJogos);
      }

      if (this.foiEmpate) {
        classificacaoAgremiacaoB.setQtdPontos(qtdPontos);
      } else if (this.golsEquipeB > this.golsEquipeA) {

        classificacaoAgremiacaoB.setQtdPontos(qtdPontos + 3);
        qtdVitorias++;
        classificacaoAgremiacaoB.setQtdVitorias(qtdVitorias);
      }
      this.repository.saveAndFlush(classificacaoAgremiacaoB);
    } else {
      this.insereClassificacaoEquipeB(jogo);
    }
  }

  private void insereClassificacaoEquipeA(Jogo jogo) {

    Classificacao classificacaoAgremiacaoA = new Classificacao();
    classificacaoAgremiacaoA.setAgremiacao(jogo.getAgremiacaoA());
    classificacaoAgremiacaoA.setGolsPro(this.golsEquipeA);
    classificacaoAgremiacaoA.setGolsContra(this.golsEquipeB);
    classificacaoAgremiacaoA.setQtdJogos(1);
    classificacaoAgremiacaoA.setQtdPontos(0);
    classificacaoAgremiacaoA.setQtdVitorias(0);

    if (this.foiEmpate) {
      classificacaoAgremiacaoA.setQtdPontos(1);
    } else if (this.golsEquipeA > this.golsEquipeB) {
      classificacaoAgremiacaoA.setQtdPontos(3);
      classificacaoAgremiacaoA.setQtdVitorias(1);
    }
    try {

      // TODO
      // Agremiação - data - hora
      final String md5 = EncryptToMD5.converterParaMD5("opa");
      classificacaoAgremiacaoA.setKeyMD5(md5);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    this.repository.saveAndFlush(classificacaoAgremiacaoA);
  }

  private void insereClassificacaoEquipeB(Jogo jogo) {

    Classificacao classificacaoAgremiacaoB = new Classificacao();
    classificacaoAgremiacaoB.setAgremiacao(jogo.getAgremiacaoB());
    classificacaoAgremiacaoB.setGolsPro(this.golsEquipeB);
    classificacaoAgremiacaoB.setGolsContra(this.golsEquipeA);
    classificacaoAgremiacaoB.setQtdJogos(1);
    classificacaoAgremiacaoB.setQtdPontos(0);
    classificacaoAgremiacaoB.setQtdVitorias(0);

    if (this.foiEmpate) {
      classificacaoAgremiacaoB.setQtdPontos(1);
    } else if (this.golsEquipeB > this.golsEquipeA) {
      classificacaoAgremiacaoB.setQtdPontos(3);
      classificacaoAgremiacaoB.setQtdVitorias(1);
    }
    this.repository.saveAndFlush(classificacaoAgremiacaoB);
  }

}
