package br.com.lufamador.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "luf_membro_tjdu")
public class MembroTjdu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private String nome;

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

        MembroTjdu that = (MembroTjdu) o;

        if (codigo != null ? !codigo.equals(that.codigo) : that.codigo != null) return false;
        if (nome != null ? !nome.equals(that.nome) : that.nome != null) return false;
        if (funcao != null ? !funcao.equals(that.funcao) : that.funcao != null) return false;
        return image != null ? image.equals(that.image) : that.image == null;
    }

    @Override
    public int hashCode() {
        int result = codigo != null ? codigo.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (funcao != null ? funcao.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MembroTjdu{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", funcao='" + funcao + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
