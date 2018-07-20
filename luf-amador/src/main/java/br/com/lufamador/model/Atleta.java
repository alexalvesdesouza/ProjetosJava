package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
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
@Table(name = "luf_atleta")
public class Atleta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private String nome;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataNascimento;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAfiliacao;
    private String identidade;
    private String cpf;
    private String observacao;
    @OneToOne
    private Endereco endereco;
    @OneToOne
    private Agremiacao agremiacao;
    private Boolean suspenso;
    @Column(columnDefinition = "TEXT")
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataAfiliacao() {
        return dataAfiliacao;
    }

    public void setDataAfiliacao(LocalDate dataAfiliacao) {
        this.dataAfiliacao = dataAfiliacao;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Agremiacao getAgremiacao() {
        return agremiacao;
    }

    public void setAgremiacao(Agremiacao agremiacao) {
        this.agremiacao = agremiacao;
    }

    public Boolean getSuspenso() {
        return suspenso;
    }

    public void setSuspenso(Boolean suspenso) {
        this.suspenso = suspenso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Atleta atleta = (Atleta) o;

        if (codigo != null ? !codigo.equals(atleta.codigo) : atleta.codigo != null) return false;
        if (nome != null ? !nome.equals(atleta.nome) : atleta.nome != null) return false;
        if (dataNascimento != null ? !dataNascimento.equals(atleta.dataNascimento) : atleta.dataNascimento != null)
            return false;
        if (dataAfiliacao != null ? !dataAfiliacao.equals(atleta.dataAfiliacao) : atleta.dataAfiliacao != null)
            return false;
        if (identidade != null ? !identidade.equals(atleta.identidade) : atleta.identidade != null) return false;
        if (cpf != null ? !cpf.equals(atleta.cpf) : atleta.cpf != null) return false;
        if (observacao != null ? !observacao.equals(atleta.observacao) : atleta.observacao != null) return false;
        if (endereco != null ? !endereco.equals(atleta.endereco) : atleta.endereco != null) return false;
        if (agremiacao != null ? !agremiacao.equals(atleta.agremiacao) : atleta.agremiacao != null) return false;
        return suspenso != null ? suspenso.equals(atleta.suspenso) : atleta.suspenso == null;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (dataNascimento != null ? dataNascimento.hashCode() : 0);
        result = 31 * result + (dataAfiliacao != null ? dataAfiliacao.hashCode() : 0);
        result = 31 * result + (identidade != null ? identidade.hashCode() : 0);
        result = 31 * result + (cpf != null ? cpf.hashCode() : 0);
        result = 31 * result + (observacao != null ? observacao.hashCode() : 0);
        result = 31 * result + (endereco != null ? endereco.hashCode() : 0);
        result = 31 * result + (agremiacao != null ? agremiacao.hashCode() : 0);
        result = 31 * result + (suspenso != null ? suspenso.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", dataAfiliacao=" + dataAfiliacao +
                ", identidade='" + identidade + '\'' +
                ", cpf='" + cpf + '\'' +
                ", observacao='" + observacao + '\'' +
                ", endereco=" + endereco +
                ", agremiacao=" + agremiacao +
                ", suspenso=" + suspenso +
                '}';
    }
}
