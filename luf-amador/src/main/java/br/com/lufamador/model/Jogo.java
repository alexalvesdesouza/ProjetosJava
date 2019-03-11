package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lufamador.lufapi.util.datas.LocalDateDeserializer;
import br.com.lufamador.lufapi.util.datas.LocalDateSerializer;
import br.com.lufamador.lufapi.util.datas.LocalDateTimeDeserializer;
import br.com.lufamador.lufapi.util.datas.LocalDateTimeSerializer;

@Entity
@Table(name = "jogo")
@SequenceGenerator(name = "jogo_seq", sequenceName = "jogo_seq", allocationSize = 1)
public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jogo_seq")
    private Long codigo;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "data_partida")
    private LocalDate dataPartida; //NOSONAR

    @Column(name = "horario_partida")
    private String horarioPartida;

    @Column(name = "gols_agremiacao_a")
    private Integer golsAgremiacaoA;

    @Column(name = "gols_agremiacao_b")
    private Integer golsAgremiacaoB;

    private String chave;
    private String rodada;
    private String fase;

    @Column(name = "partida_encerrada")
    private Boolean partidaEncerrada;

    @Column(name = "key_confronto")
    private String keyConfronto;

    private String turno;

    @Column(name = "w_agremiacao_a")
    private boolean wAgremiacaoA;

    @Column(name = "w_agremiacao_b")
    private boolean wAgremiacaoB;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao; //NOSONAR

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao; //NOSONAR

    @Column(name = "gols_penaltis_agremiacao_a")
    private Integer golsPenaltisAgremiacaoA;

    @Column(name = "gols_penaltis_agremiacao_b")
    private Integer golsPenaltisAgremiacaoB;

    @Column(name = "decisao_penaltis")
    private boolean penaltis;

    private String temporada;

    @OneToOne
    @JoinColumn(name = "codigo_local_jogo")
    private LocalJogo localJogo;

    @OneToOne
    @JoinColumn(name = "codigo_agremiacao_a")
    private Agremiacao agremiacaoA;

    @OneToOne
    @JoinColumn(name = "codigo_agremiacao_b")
    private Agremiacao agremiacaoB;

    @OneToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;
//    private String categoria;

    @Column(name = "codigo_competicao")
    private Integer codigoCompeticao;

    public Integer getCodigoCompeticao() {
        return codigoCompeticao;
    }

    public void setCodigoCompeticao(Integer codigoCompeticao) {
        this.codigoCompeticao = codigoCompeticao;
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

    public LocalJogo getLocalJogo() {
        return localJogo;
    }

    public void setLocalJogo(LocalJogo localJogo) {
        this.localJogo = localJogo;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
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

    public boolean isPenaltis() {
        return penaltis;
    }

    public void setPenaltis(boolean penaltis) {
        this.penaltis = penaltis;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogo jogo = (Jogo) o;
        return Objects.equals(codigo, jogo.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
