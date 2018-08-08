package br.com.lufamador.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "luf_arbitro")
public class Arbitro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private String nome;

    private String telefone;

    private String funcao;

    @Column(columnDefinition = "TEXT")
    private String image;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arbitro arbitro = (Arbitro) o;

        if (codigo != null ? !codigo.equals(arbitro.codigo) : arbitro.codigo != null) return false;
        if (nome != null ? !nome.equals(arbitro.nome) : arbitro.nome != null) return false;
        if (telefone != null ? !telefone.equals(arbitro.telefone) : arbitro.telefone != null) return false;
        if (funcao != null ? !funcao.equals(arbitro.funcao) : arbitro.funcao != null) return false;
        return image != null ? image.equals(arbitro.image) : arbitro.image == null;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (funcao != null ? funcao.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Arbitro{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", funcao='" + funcao + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
