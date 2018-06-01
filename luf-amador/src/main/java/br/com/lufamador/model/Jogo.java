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
    private LocalDate dataPartida;
    private String horario;
    @OneToOne
    private Local local;
    @OneToOne
    private Agremiacao equipeA;
    @OneToOne
    private Agremiacao equipeB;
    private Integer golsEquipeA;
    private Integer golsEquipeB;
    private String chave;
    private Boolean partidaEncerrada;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataCriacao;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataAtualizacao;

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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Agremiacao getEquipeA() {
        return equipeA;
    }

    public void setEquipeA(Agremiacao equipeA) {
        this.equipeA = equipeA;
    }

    public Agremiacao getEquipeB() {
        return equipeB;
    }

    public void setEquipeB(Agremiacao equipeB) {
        this.equipeB = equipeB;
    }

    public Integer getGolsEquipeA() {
        return golsEquipeA;
    }

    public void setGolsEquipeA(Integer golsEquipeA) {
        this.golsEquipeA = golsEquipeA;
    }

    public Integer getGolsEquipeB() {
        return golsEquipeB;
    }

    public void setGolsEquipeB(Integer golsEquipeB) {
        this.golsEquipeB = golsEquipeB;
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
}
