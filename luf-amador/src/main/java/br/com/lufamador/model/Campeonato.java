package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lufamador.utils.datas.LocalDateDeserializer;
import br.com.lufamador.utils.datas.LocalDateSerializer;

@Entity
@Table(name = "luf_campeonato")
public class Campeonato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private String nomeCampeonato;
    private String categoria;
    private String edicao;
    private String cor;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataInicio;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataEncerramento;
    private Boolean campeonatoEncerrado;
    private Boolean inscricoesEncerradas;

    @OneToMany
    private List<Agremiacao> inscricoes;
    @OneToOne
    private TabelaJogos tabelaJogos;

    public TabelaJogos getTabelaJogos() {
        return tabelaJogos;
    }

    public void setTabelaJogos(TabelaJogos tabelaJogos) {
        this.tabelaJogos = tabelaJogos;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNomeCampeonato() {
        return nomeCampeonato;
    }

    public void setNomeCampeonato(String nomeCampeonato) {
        this.nomeCampeonato = nomeCampeonato;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDate dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Boolean getCampeonatoEncerrado() {
        return campeonatoEncerrado;
    }

    public void setCampeonatoEncerrado(Boolean campeonatoEncerrado) {
        this.campeonatoEncerrado = campeonatoEncerrado;
    }

    public Boolean getInscricoesEncerradas() {
        return inscricoesEncerradas;
    }

    public void setInscricoesEncerradas(Boolean inscricoesEncerradas) {
        this.inscricoesEncerradas = inscricoesEncerradas;
    }

    public List<Agremiacao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Agremiacao> inscricoes) {
        this.inscricoes = inscricoes;
    }
}
