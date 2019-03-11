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
@Table(name = "intervencao")
@SequenceGenerator(name = "intervencao_seq", sequenceName = "intervencao_seq", allocationSize = 1)
public class Intervencao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "intervencao_seq")
    private Long codigo;

    private Integer pontosGanhos;
    private Integer pontosPerdidos;
    private String  motivo;
    private boolean decisaoTribunal;

//    @ManyToOne
//    @OneToOne
//    private Classificacao classificacao;

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

//    public Classificacao getClassificacao() {
//        return classificacao;
//    }
//
//    public void setClassificacao(Classificacao classificacao) {
//        this.classificacao = classificacao;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intervencao that = (Intervencao) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
