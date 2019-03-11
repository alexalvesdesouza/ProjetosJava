package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lufamador.lufapi.util.datas.LocalDateDeserializer;
import br.com.lufamador.lufapi.util.datas.LocalDateSerializer;

@Entity
@Table(name = "campeonato")
@SequenceGenerator(name = "campeonato_seq", sequenceName = "campeonato_seq", allocationSize = 1)
public class Campeonato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campeonato_seq")
    private Long codigo;

    private String nome;

    @OneToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    private String edicao;
    private String cor;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    @Column(name = "campeonato_encerrado")
    private Boolean campeonatoEncerrado;

    @Column(name = "inscricoes_encerradas")
    private Boolean inscricoesEncerradas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "campeonato_agremiacao", joinColumns = @JoinColumn(name = "codigo_campeonato")
            , inverseJoinColumns = @JoinColumn(name = "codigo_agremiacao"))
    private List<Agremiacao> inscricoes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "campeonato_jogo", joinColumns = @JoinColumn(name = "codigo_campeonato")
            , inverseJoinColumns = @JoinColumn(name = "codigo_jogo"))
    private List<Jogo> jogos;

    private String temporada;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
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
        Campeonato that = (Campeonato) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
