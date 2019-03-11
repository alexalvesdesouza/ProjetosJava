package br.com.lufamador.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "local_jogo")
@SequenceGenerator(name = "local_jogo_seq", sequenceName = "local_jogo_seq", allocationSize = 1)
public class LocalJogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "local_jogo_seq")
    private Long codigo;

    private String nome;

    private String tipo;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalJogo localJogo = (LocalJogo) o;

        if (codigo != null ? !codigo.equals(localJogo.codigo) : localJogo.codigo != null) return false;
        if (nome != null ? !nome.equals(localJogo.nome) : localJogo.nome != null) return false;
        return tipo != null ? tipo.equals(localJogo.tipo) : localJogo.tipo == null;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (tipo != null ? tipo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocalJogo{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
