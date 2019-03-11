package br.com.lufamador.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "recibo")
@SequenceGenerator(name = "recibo_seq", sequenceName = "recibo_seq", allocationSize = 1)
public class Recibo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recibo_seq")
    private Long codigo;

    private String numero;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private String temporada;

    @Column(columnDefinition = "TEXT")
    private String referencia;

    @Column(name = "agremiacao_destino")
    private String agremiacaoDestino;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria_financ")
    private CategoriaFinanceiro categoriaFinanceiro;

    @ManyToOne
    @JoinColumn(name = "codigo_agremiacao")
    private Agremiacao agremiacao;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    private Categoria categoria;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "recibo_atleta", joinColumns = @JoinColumn(name = "codigo_recibo")
            , inverseJoinColumns = @JoinColumn(name = "codigo_atleta"))
    private List<Atleta> atletas;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "recibo_membro", joinColumns = @JoinColumn(name = "codigo_recibo")
            , inverseJoinColumns = @JoinColumn(name = "codigo_membro"))
    private List<MembroAgremiacao> membros;

    public List<MembroAgremiacao> getMembros() {
        return membros;
    }

    public void setMembros(List<MembroAgremiacao> membros) {
        this.membros = membros;
    }

    public String getAgremiacaoDestino() {
        return agremiacaoDestino;
    }

    public void setAgremiacaoDestino(String agremiacaoDestino) {
        this.agremiacaoDestino = agremiacaoDestino;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public CategoriaFinanceiro getCategoriaFinanceiro() {
        return categoriaFinanceiro;
    }

    public void setCategoriaFinanceiro(CategoriaFinanceiro categoriaFinanceiro) {
        this.categoriaFinanceiro = categoriaFinanceiro;
    }

    public Agremiacao getAgremiacao() {
        return agremiacao;
    }

    public void setAgremiacao(Agremiacao agremiacao) {
        this.agremiacao = agremiacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Atleta> getAtletas() {
        return atletas;
    }

    public void setAtletas(List<Atleta> atletas) {
        this.atletas = atletas;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recibo that = (Recibo) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }


}
