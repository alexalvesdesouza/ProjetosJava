package br.com.lufamador.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "luf_chave")
public class Chave implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private Integer numeroChave;
    private String nomeChave;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getNumeroChave() {
        return numeroChave;
    }

    public void setNumeroChave(Integer numeroChave) {
        this.numeroChave = numeroChave;
    }

    public String getNomeChave() {
        return nomeChave;
    }

    public void setNomeChave(String nomeChave) {
        this.nomeChave = nomeChave;
    }
}
