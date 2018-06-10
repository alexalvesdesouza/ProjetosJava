package br.com.lufamador.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lufamador.exception.BussinessException;
import br.com.lufamador.model.Agremiacao;
import br.com.lufamador.repository.AgremiacaoRepository;
import br.com.lufamador.utils.mensagens.MensagensErro;
import br.com.lufamador.validate.AgremiacaoValidate;

@Service
public class AgremiacaoService {

  private final AgremiacaoRepository repository;
  private final AgremiacaoValidate   validate;
  private final EnderecoService      enderecoService;

  @Autowired
  public AgremiacaoService(AgremiacaoRepository repository, AgremiacaoValidate validate, EnderecoService enderecoService) {
    this.repository = repository;
    this.validate = validate;
    this.enderecoService = enderecoService;
  }

  public Agremiacao cadastraAgremiacao(Agremiacao agremiacao) {
    Agremiacao agremiacaoSaved = null;
    agremiacao.setNomeSigla(this.getNomeSigla(agremiacao.getNome()));

    this.validate.validaCadastroAgremiacao(agremiacao);
    try {
      this.enderecoService.cadastraEndereco(agremiacao.getEndereco());
      this.calculaDataMandatoDiretoria(agremiacao);
      if (null == agremiacao.getInativa())
        agremiacao.setInativa(false);
      agremiacaoSaved = this.repository.saveAndFlush(agremiacao);
    } catch (Exception e) {
    }
    return agremiacaoSaved;
  }

  private void calculaDataMandatoDiretoria(Agremiacao agremiacao) {
    final LocalDate dataAfiliacao = agremiacao.getDataAfiliacao();
    final LocalDate dataAtual = LocalDate.now();
    LocalDate dataMandatoDiretoria = dataAfiliacao.plusYears(4l);

    boolean finalizouCalculo = false;

    while (!finalizouCalculo) {

      if (dataMandatoDiretoria.isAfter(dataAtual)) {
        finalizouCalculo = true;
      } else {
        dataMandatoDiretoria = dataMandatoDiretoria.plusYears(4l);
      }
    }
    agremiacao.setDataMandatoDiretoria(dataMandatoDiretoria);
  }

  public final String getNomeSigla(String sigla) {

    String siglaRetorno = sigla.toLowerCase();
    siglaRetorno = siglaRetorno.replace(" ", "_")
                               .replace("ç", "c")
                               .replace("á", "a")
                               .replace("à", "a")
                               .replace("â", "a")
                               .replace("ã", "a")
                               .replace("é", "e")
                               .replace("è", "e")
                               .replace("ê", "e")
                               .replace("ẽ", "e")
                               .replace("í", "i")
                               .replace("ì", "i")
                               .replace("î", "i")
                               .replace("ĩ", "i")
                               .replace("ó", "o")
                               .replace("ò", "o")
                               .replace("ô", "o")
                               .replace("õ", "o")
                               .replace("ú", "u")
                               .replace("ù", "u")
                               .replace("û", "u")
                               .replace("ũ", "u");

    return siglaRetorno.toUpperCase();
  }

  public List<Agremiacao> getAgremiacoes() {
    return this.repository.findAll();
  }

  public List<Agremiacao> getAgremiacoesInscritas(final Long codigoCampeonato) {
    return this.repository.getAgremiacoesInscritas(codigoCampeonato);
  }

  public List<Agremiacao> getAgremiacoesDisponiveis(final Long codigoCampeonato) {
    return this.repository.getAgremiacoesDisponiveis(codigoCampeonato)
                          .stream()
                          .filter(agremiacao -> !agremiacao.getInativa())
                          .collect(Collectors.toList());
  }

  public final Agremiacao atualizarAgremiacao(Agremiacao agremiacao) {
    this.validate.validaAtualizacaoAgremiacao(agremiacao);
    this.enderecoService.atualizaEndereco(agremiacao.getEndereco());
    this.calculaDataMandatoDiretoria(agremiacao);
    return this.repository.saveAndFlush(agremiacao);
  }

  public void deletarAgremiacao(final Long codigo) {
    final Agremiacao agremiacao = this.getAgremiacao(codigo);
    this.validate.deletarAgremiacao(agremiacao);
    this.repository.delete(agremiacao);
  }

  public Agremiacao getAgremiacao(final Long codigo) {
    Optional<Agremiacao> agremiacao = this.repository.findById(codigo);
    if (!agremiacao.isPresent())
      throw new BussinessException(MensagensErro.ENTIDADE_INEXISTENTE);
    return agremiacao.get();
  }
}
