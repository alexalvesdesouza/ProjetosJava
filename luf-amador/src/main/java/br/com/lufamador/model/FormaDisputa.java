package br.com.lufamador.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "luf_forma_disputa")
public class FormaDisputa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private boolean pontosCorridos;

    private Integer qtdChaves;
    private Integer qtdClassPorChave;

    @OneToMany
    private List<Fase> fases;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public boolean isPontosCorridos() {
        return pontosCorridos;
    }

    public void setPontosCorridos(boolean pontosCorridos) {
        this.pontosCorridos = pontosCorridos;
    }

    public Integer getQtdChaves() {
        return qtdChaves;
    }

    public void setQtdChaves(Integer qtdChaves) {
        this.qtdChaves = qtdChaves;
    }

    public Integer getQtdClassPorChave() {
        return qtdClassPorChave;
    }

    public void setQtdClassPorChave(Integer qtdClassPorChave) {
        this.qtdClassPorChave = qtdClassPorChave;
    }

    public List<Fase> getFases() {
        return fases;
    }

    public void setFases(List<Fase> fases) {
        this.fases = fases;
    }
}
