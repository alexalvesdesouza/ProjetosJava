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
    private String email;
    private String telefone;
    @OneToOne
    private Endereco endereco;
    @OneToOne
    private Agremiacao agremiacao;
    private Boolean suspenso;

    public Agremiacao getAgremiacao() {
        return agremiacao;
    }

    public void setAgremiacao(Agremiacao agremiacao) {
        this.agremiacao = agremiacao;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Boolean getSuspenso() {
        return suspenso;
    }

    public void setSuspenso(Boolean suspenso) {
        this.suspenso = suspenso;
    }
}
