package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lufamador.utils.datas.LocalDateDeserializer;
import br.com.lufamador.utils.datas.LocalDateSerializer;
import br.com.lufamador.utils.datas.LocalDateTimeDeserializer;
import br.com.lufamador.utils.datas.LocalDateTimeSerializer;

@Entity
@Table(name = "luf_jogo")
public class Jogo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  private Long              codigo;

  @JsonSerialize(using = LocalDateSerializer.class)
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate         dataPartida;
  private String            horarioPartida;
  // @OneToOne
  private String            local;
  @OneToOne
  private Agremiacao        agremiacaoA;
  @OneToOne
  private Agremiacao        agremiacaoB;
  private Integer           golsAgremiacaoA;
  private Integer           golsAgremiacaoB;
  private Integer           codigoCompeticao;
  private String            chave;
  private String            rodada;
  private Boolean           partidaEncerrada;
  private String            keyConfronto;

  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime     dataCriacao;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime     dataAtualizacao;

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public LocalDate getDataPartida() {
    return dataPartida;
  }

  public void setDataPartida(LocalDate dataPartida) {
    this.dataPartida = dataPartida;
  }

  public String getHorarioPartida() {
    return horarioPartida;
  }

  public void setHorarioPartida(String horarioPartida) {
    this.horarioPartida = horarioPartida;
  }

  public String getLocal() {
    return local;
  }

  public void setLocal(String local) {
    this.local = local;
  }

  public Agremiacao getAgremiacaoA() {
    return agremiacaoA;
  }

  public void setAgremiacaoA(Agremiacao agremiacaoA) {
    this.agremiacaoA = agremiacaoA;
  }

  public Agremiacao getAgremiacaoB() {
    return agremiacaoB;
  }

  public void setAgremiacaoB(Agremiacao agremiacaoB) {
    this.agremiacaoB = agremiacaoB;
  }

  public Integer getGolsAgremiacaoA() {
    return golsAgremiacaoA;
  }

  public void setGolsAgremiacaoA(Integer golsAgremiacaoA) {
    this.golsAgremiacaoA = golsAgremiacaoA;
  }

  public Integer getGolsAgremiacaoB() {
    return golsAgremiacaoB;
  }

  public void setGolsAgremiacaoB(Integer golsAgremiacaoB) {
    this.golsAgremiacaoB = golsAgremiacaoB;
  }

  public String getChave() {
    return chave;
  }

  public void setChave(String chave) {
    this.chave = chave;
  }

  public String getRodada() {
    return rodada;
  }

  public void setRodada(String rodada) {
    this.rodada = rodada;
  }

  public Boolean getPartidaEncerrada() {
    return partidaEncerrada;
  }

  public void setPartidaEncerrada(Boolean partidaEncerrada) {
    this.partidaEncerrada = partidaEncerrada;
  }

  public String getKeyConfronto() {
    return keyConfronto;
  }

  public void setKeyConfronto(String keyConfronto) {
    this.keyConfronto = keyConfronto;
  }

  public LocalDateTime getDataCriacao() {
    return dataCriacao;
  }

  public void setDataCriacao(LocalDateTime dataCriacao) {
    this.dataCriacao = dataCriacao;
  }

  public LocalDateTime getDataAtualizacao() {
    return dataAtualizacao;
  }

  public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
    this.dataAtualizacao = dataAtualizacao;
  }

  public Integer getCodigoCompeticao() {
    return codigoCompeticao;
  }

  public void setCodigoCompeticao(Integer codigoCompeticao) {
    this.codigoCompeticao = codigoCompeticao;
  }
}
