package br.com.lufamador.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.lufamador.model.Atleta;
import br.com.lufamador.repository.AtletaRepository;
import br.com.lufamador.service.AtletaService;
import br.com.lufamador.validate.AtletaValidate;

@Service
public class AtletaServiceImpl implements AtletaService {

  private final AtletaRepository  repository;
  private final AtletaValidate    validate;
  private final EnderecoService   enderecoService;
  private final AgremiacaoService agremiacaoService;

  @Autowired
  public AtletaServiceImpl(AtletaRepository repository, AtletaValidate validate, EnderecoService enderecoService,
      AgremiacaoService agremiacaoService) {
    this.repository = repository;
    this.validate = validate;
    this.enderecoService = enderecoService;
    this.agremiacaoService = agremiacaoService;
  }

  private Atleta cadastraAtleta(Atleta atleta) {
    Atleta atletaSaved = null;
    this.validate.validaAtletaExistente(atleta);
    try {
      if (atleta.getEndereco() != null)
        this.enderecoService.cadastraEndereco(atleta.getEndereco());

      atleta.setDataAfiliacao(LocalDate.now());
      atleta.setSuspenso(Boolean.FALSE);
      atletaSaved = this.repository.saveAndFlush(atleta);
    } catch (Exception e) {

    }
    return atletaSaved;
  }

  private Atleta atulizarAtleta(final Atleta atletaAtualizar) {
    Atleta atletaAtualizado = null;
    final Optional<Atleta> atleta = this.repository.findById(atletaAtualizar.getCodigo());
    if (atleta.isPresent()) {
      this.enderecoService.atualizaEndereco(atletaAtualizar.getEndereco());
      this.agremiacaoService.atualizarAgremiacao(atletaAtualizar.getAgremiacao());
      atletaAtualizado = this.repository.saveAndFlush(atletaAtualizar);
    }
    return atletaAtualizado;
  }

  public final Atleta suspenderAtleta(final Long codigo) {
    Atleta atletaSuspenso = null;
    final Optional<Atleta> atleta = this.repository.findById(codigo);
    if (atleta.isPresent()) {
      atleta.get()
            .setSuspenso(Boolean.TRUE);
      atletaSuspenso = this.repository.saveAndFlush(atleta.get());
    }
    return atletaSuspenso;
  }

  public final Atleta baixarSuspensaoAtleta(final Long codigo) {
    Atleta atletaSuspenso = null;
    final Optional<Atleta> atleta = this.repository.findById(codigo);
    if (atleta.isPresent()) {
      atleta.get()
            .setSuspenso(Boolean.FALSE);
      atletaSuspenso = this.repository.saveAndFlush(atleta.get());
    }
    return atletaSuspenso;
  }

  private void excluirAtleta(final Long codigo) {
    final Optional<Atleta> atleta = this.repository.findById(codigo);
    if (atleta.isPresent())
      this.repository.delete(atleta.get());

  }
  //
  // public List<Atleta> getAtletas() {
  // return this.repository.findAll();
  // }

  @Override
  public Page<Atleta> findAll(int page, int count) {
    Pageable pages = PageRequest.of(page, count);
    return this.repository.findAll(pages);
  }

  @Override
  public Atleta createOrUpdate(Atleta atleta) {
    if (atleta.getCodigo() != null)
      return this.atulizarAtleta(atleta);
    return this.cadastraAtleta(atleta);
  }

  @Override
  public Atleta findById(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void delete(Long id) {
    this.excluirAtleta(id);
  }
}
