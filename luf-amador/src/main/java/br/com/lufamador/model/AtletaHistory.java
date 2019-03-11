package br.com.lufamador.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "atleta_history")
@SequenceGenerator(name = "atleta_history_seq", sequenceName = "atleta_history_seq", allocationSize = 1)
public class AtletaHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atleta_history_seq")
    private Long codigo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "codigo_agremiacao")
    private Agremiacao agremiacao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "codigo_atleta")
    private Atleta atleta;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    private Integer temporada;

    private String observacao;


    public AtletaHistory() {

    }

    public AtletaHistory(Agremiacao agremiacao, Atleta atleta, Categoria categoria, Integer temporada,
            String observacao) {
        this.agremiacao = agremiacao;
        this.atleta = atleta;
        this.categoria = categoria;
        this.temporada = temporada;
        this.observacao = observacao;
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

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtletaHistory that = (AtletaHistory) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
