package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.lufamador.utils.datas.LocalDateDeserializer;
import br.com.lufamador.utils.datas.LocalDateSerializer;


@Entity
@Table(name = "agremiacao")
@SequenceGenerator(name = "agremiacao_seq", sequenceName = "agremiacao_seq", allocationSize = 1)
public class Agremiacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agremiacao_seq")
    private Long codigo;

    private String nome;
    private String nomeSigla;
    private Boolean inativa;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fundacao;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataMandatoDiretoria;

    private String observacao;
    private String anexo;

    @Transient
    private String urlAnexo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "agremiacao_membro", joinColumns = @JoinColumn(name = "codigo_agremiacao")
            , inverseJoinColumns = @JoinColumn(name = "codigo_membro"))
    private List<MembroAgremiacao> membros;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "agremiacao_categoria", joinColumns = @JoinColumn(name = "codigo_agremiacao")
            , inverseJoinColumns = @JoinColumn(name = "codigo_categoria"))
    private List<Categoria> categorias;

    public String getUrlAnexo() {
        return urlAnexo;
    }

    public void setUrlAnexo(String urlAnexo) {
        this.urlAnexo = urlAnexo;
    }

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

    public String getNomeSigla() {
        return nomeSigla;
    }

    public void setNomeSigla(String nomeSigla) {
        this.nomeSigla = nomeSigla;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Boolean getInativa() {
        return inativa;
    }

    @JsonIgnore
    public Boolean isAtiva() {
        return !this.inativa;
    }

    public void setInativa(Boolean inativa) {
        this.inativa = inativa;
    }

    public LocalDate getFundacao() {
        return fundacao;
    }

    public void setFundacao(LocalDate fundacao) {
        this.fundacao = fundacao;
    }

    public LocalDate getDataMandatoDiretoria() {
        return dataMandatoDiretoria;
    }

    public void setDataMandatoDiretoria(LocalDate dataMandatoDiretoria) {
        this.dataMandatoDiretoria = dataMandatoDiretoria;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<MembroAgremiacao> getMembros() {
        return membros;
    }

    public void setMembros(List<MembroAgremiacao> membros) {
        this.membros = membros;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agremiacao that = (Agremiacao) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

}
