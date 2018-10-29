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
    private Long codigo;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataPartida; //NOSONAR
    private String horarioPartida;
    private String local;
    @OneToOne
    private LocalJogo localJogo;
    @OneToOne
    private Agremiacao agremiacaoA;
    @OneToOne
    private Agremiacao agremiacaoB;
    private Integer golsAgremiacaoA;
    private Integer golsAgremiacaoB;
    private Integer codigoCompeticao;
    private String chave;
    private String rodada;
    private String fase;
    private Boolean partidaEncerrada;
    private String keyConfronto;
    private String categoria;
    private String turno;
    private boolean wAgremiacaoA;
    private boolean wAgremiacaoB;


    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataCriacao; //NOSONAR

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataAtualizacao; //NOSONAR

    private Integer golsPenaltisAgremiacaoA;
    private Integer golsPenaltisAgremiacaoB;
    private boolean penaltis;

    public boolean isPenaltis() {
        return penaltis;
    }

    public void setPenaltis(boolean penaltis) {
        this.penaltis = penaltis;
    }

    public Integer getGolsPenaltisAgremiacaoA() {
        return golsPenaltisAgremiacaoA;
    }

    public void setGolsPenaltisAgremiacaoA(Integer golsPenaltisAgremiacaoA) {
        this.golsPenaltisAgremiacaoA = golsPenaltisAgremiacaoA;
    }

    public Integer getGolsPenaltisAgremiacaoB() {
        return golsPenaltisAgremiacaoB;
    }

    public void setGolsPenaltisAgremiacaoB(Integer golsPenaltisAgremiacaoB) {
        this.golsPenaltisAgremiacaoB = golsPenaltisAgremiacaoB;
    }

    public boolean iswAgremiacaoA() {
        return wAgremiacaoA;
    }

    public void setwAgremiacaoA(boolean wAgremiacaoA) {
        this.wAgremiacaoA = wAgremiacaoA;
    }

    public boolean iswAgremiacaoB() {
        return wAgremiacaoB;
    }

    public void setwAgremiacaoB(boolean wAgremiacaoB) {
        this.wAgremiacaoB = wAgremiacaoB;
    }

    public LocalJogo getLocalJogo() {
        return localJogo;
    }

    public void setLocalJogo(LocalJogo localJogo) {
        this.localJogo = localJogo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRodada() {
        return rodada;
    }

    public void setRodada(String rodada) {
        this.rodada = rodada;
    }

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

    public Integer getCodigoCompeticao() {
        return codigoCompeticao;
    }

    public void setCodigoCompeticao(Integer codigoCompeticao) {
        this.codigoCompeticao = codigoCompeticao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
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

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
