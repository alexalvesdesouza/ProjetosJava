package br.com.lufamador.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "classificacao")
@SequenceGenerator(name = "classificacao_seq", sequenceName = "classificacao_seq", allocationSize = 1)
public class Classificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classificacao_seq")
    private Long codigo;

    private Integer campeonatoCodigo;
    private Integer posClassificacao;
    private Integer qtdJogos;
    private Integer qtdPontos;
    private Integer qtdVitorias;
    private Integer qtdEmpates;
    private Integer golsPro;
    private Integer golsContra;
    private String chave;
    private String keyMD5;
    private Boolean classificada;
    private String fase;

    @OneToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    @OneToOne
    @JoinColumn(name = "codigo_agremiacao")
    private Agremiacao agremiacao;

    @ManyToMany
    @JoinTable(name = "classificacao_intervencao", joinColumns = @JoinColumn(name = "codigo_classificacao")
            , inverseJoinColumns = @JoinColumn(name = "codigo_intervencao"))
    private List<Intervencao> intervencoes;

    @Transient
    private Intervencao last;

    private String temporada;

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public Classificacao() {
        super();
    }

    public Classificacao(Agremiacao agremiacao, Integer campeonatoCodigo, Integer posClassificacao, Integer qtdJogos,
            Integer qtdPontos, Integer qtdVitorias, Integer qtdEmpates, Integer golsPro, Integer golsContra,
            String chave, String keyMD5, Boolean classificada, String fase, Categoria categoria) {
        this.agremiacao = agremiacao;
        this.campeonatoCodigo = campeonatoCodigo;
        this.posClassificacao = posClassificacao;
        this.qtdJogos = qtdJogos;
        this.qtdPontos = qtdPontos;
        this.qtdVitorias = qtdVitorias;
        this.qtdEmpates = qtdEmpates;
        this.golsPro = golsPro;
        this.golsContra = golsContra;
        this.chave = chave;
        this.keyMD5 = keyMD5;
        this.classificada = classificada;
        this.fase = fase;
        this.categoria = categoria;
    }

    public Intervencao getLast() {
        return last;
    }

    public void setLast(Intervencao last) {
        this.last = last;
    }

    public List<Intervencao> getIntervencoes() {
        return intervencoes;
    }

    public void setIntervencoes(List<Intervencao> intervencoes) {
        this.intervencoes = intervencoes;
    }

    public Integer getCampeonatoCodigo() {
        return campeonatoCodigo;
    }

    public void setCampeonatoCodigo(Integer campeonatoCodigo) {
        this.campeonatoCodigo = campeonatoCodigo;
    }

    public Integer getQtdEmpates() {
        return qtdEmpates;
    }

    public void setQtdEmpates(Integer qtdEmpates) {
        this.qtdEmpates = qtdEmpates;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Boolean getClassificada() {
        return classificada;
    }

    public void setClassificada(Boolean classificada) {
        this.classificada = classificada;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getKeyMD5() {
        return keyMD5;
    }

    public void setKeyMD5(String keyMD5) {
        this.keyMD5 = keyMD5;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Agremiacao getAgremiacao() {
        return agremiacao;
    }

    public void setAgremiacao(Agremiacao agremiacao) {
        this.agremiacao = agremiacao;
    }

    public Integer getPosClassificacao() {
        return posClassificacao;
    }

    public void setPosClassificacao(Integer posClassificacao) {
        this.posClassificacao = posClassificacao;
    }

    public Integer getQtdJogos() {
        return qtdJogos;
    }

    public void setQtdJogos(Integer qtdJogos) {
        this.qtdJogos = qtdJogos;
    }

    public Integer getQtdPontos() {
        return qtdPontos;
    }

    public void setQtdPontos(Integer qtdPontos) {
        this.qtdPontos = qtdPontos;
    }

    public Integer getQtdVitorias() {
        return qtdVitorias;
    }

    public void setQtdVitorias(Integer qtdVitorias) {
        this.qtdVitorias = qtdVitorias;
    }

    public Integer getGolsPro() {
        return golsPro;
    }

    public void setGolsPro(Integer golsPro) {
        this.golsPro = golsPro;
    }

    public Integer getGolsContra() {
        return golsContra;
    }

    public void setGolsContra(Integer golsContra) {
        this.golsContra = golsContra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classificacao that = (Classificacao) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
