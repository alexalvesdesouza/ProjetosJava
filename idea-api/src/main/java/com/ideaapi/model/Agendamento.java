package com.ideaapi.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ideaapi.enums.TipoAgendamento;

@Entity
@Table(name = "agendamento")
@SequenceGenerator(name = "agendamento_seq", sequenceName = "agendamento_seq", allocationSize = 1)
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agendamento_seq")
    private Long codigo;

    @NotNull
    @Size(min = 3, max = 100)
    private String observacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoAgendamento tipo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "codigo_horario")
    private Horario horario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "codigo_funcionario")
    private Funcionario funcionario;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public TipoAgendamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAgendamento tipo) {
        this.tipo = tipo;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
