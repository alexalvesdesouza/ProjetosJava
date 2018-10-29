package br.com.lufamador.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "luf_intervencao")
public class Intervencao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private Integer pontosGanhos;
    private Integer pontosPerdidos;
    private String  motivo;
    private boolean decisaoTribunal;

    @ManyToOne
    private Classificacao classificacao;

    public Intervencao() {
        super();
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Integer getPontosGanhos() {
        return pontosGanhos;
    }

    public void setPontosGanhos(Integer pontosGanhos) {
        this.pontosGanhos = pontosGanhos;
    }

    public Integer getPontosPerdidos() {
        return pontosPerdidos;
    }

    public void setPontosPerdidos(Integer pontosPerdidos) {
        this.pontosPerdidos = pontosPerdidos;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isDecisaoTribunal() {
        return decisaoTribunal;
    }

    public void setDecisaoTribunal(boolean decisaoTribunal) {
        this.decisaoTribunal = decisaoTribunal;
    }

    public Classificacao getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Classificacao classificacao) {
        this.classificacao = classificacao;
    }
}
