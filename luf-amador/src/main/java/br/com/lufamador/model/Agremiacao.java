package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;

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

@Entity
@Table(name = "luf_agremiacao")
public class Agremiacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private String nome;
    private String nomeSigla;
    private String linkEscudo;
    private String inativa;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAfiliacao;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataMandatoDiretoria;
    private String observacao;

    @OneToOne
    private Endereco endereco;

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

    public String getLinkEscudo() {
        return linkEscudo;
    }

    public void setLinkEscudo(String linkEscudo) {
        this.linkEscudo = linkEscudo;
    }

    public String getInativa() {
        return inativa;
    }

    public void setInativa(String inativa) {
        this.inativa = inativa;
    }

    public LocalDate getDataAfiliacao() {
        return dataAfiliacao;
    }

    public void setDataAfiliacao(LocalDate dataAfiliacao) {
        this.dataAfiliacao = dataAfiliacao;
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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agremiacao that = (Agremiacao) o;

        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (nomeSigla != null ? !nomeSigla.equals(that.nomeSigla) : that.nomeSigla != null) return false;
        if (linkEscudo != null ? !linkEscudo.equals(that.linkEscudo) : that.linkEscudo != null) return false;
        if (inativa != null ? !inativa.equals(that.inativa) : that.inativa != null) return false;
        if (dataAfiliacao != null ? !dataAfiliacao.equals(that.dataAfiliacao) : that.dataAfiliacao != null)
            return false;
        if (dataMandatoDiretoria != null ? !dataMandatoDiretoria.equals(
                that.dataMandatoDiretoria) : that.dataMandatoDiretoria != null) return false;
        if (observacao != null ? !observacao.equals(that.observacao) : that.observacao != null) return false;
        return endereco != null ? endereco.equals(that.endereco) : that.endereco == null;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (nomeSigla != null ? nomeSigla.hashCode() : 0);
        result = 31 * result + (linkEscudo != null ? linkEscudo.hashCode() : 0);
        result = 31 * result + (inativa != null ? inativa.hashCode() : 0);
        result = 31 * result + (dataAfiliacao != null ? dataAfiliacao.hashCode() : 0);
        result = 31 * result + (dataMandatoDiretoria != null ? dataMandatoDiretoria.hashCode() : 0);
        result = 31 * result + (observacao != null ? observacao.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Agremiacao{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", nomeSigla='" + nomeSigla + '\'' +
                ", linkEscudo='" + linkEscudo + '\'' +
                ", inativa='" + inativa + '\'' +
                ", dataAfiliacao=" + dataAfiliacao +
                ", dataMandatoDiretoria=" + dataMandatoDiretoria +
                ", observacao='" + observacao + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}
