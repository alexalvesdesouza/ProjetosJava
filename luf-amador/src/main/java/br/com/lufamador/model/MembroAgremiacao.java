package br.com.lufamador.model;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "membro_agremiacao")
@SequenceGenerator(name = "membro_agremiacao_seq", sequenceName = "membro_agremiacao_seq", allocationSize = 1)
public class MembroAgremiacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membro_agremiacao_seq")
    private Long codigo;
    private String nome;
    private String funcao;

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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembroAgremiacao membroAgremiacao = (MembroAgremiacao) o;
        return Objects.equals(codigo, membroAgremiacao.codigo) &&
                Objects.equals(nome, membroAgremiacao.nome) &&
                Objects.equals(funcao, membroAgremiacao.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome, funcao);
    }
}