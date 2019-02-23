package br.com.lufamador.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "luf_classificacao_history")
public class ClassificacaoHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    @OneToOne
    private Agremiacao agremiacao;
    private Integer campeonatoCodigo;

    private Integer posClassificacao;
    private Integer qtdJogos;
    private Integer qtdPontos;
    private Integer qtdVitorias;
    private Integer qtdEmpates;
    private Integer golsPro;
    private Integer golsContra;
    private String chave;
    private Boolean classificada;
    private String fase;
    private String categoria;

    @Transient
    private Intervencao last;

    private String temporada;

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public ClassificacaoHistory() {
        super();
    }

    public Intervencao getLast() {
        return last;
    }

    public void setLast(Intervencao last) {
        this.last = last;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
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
}
