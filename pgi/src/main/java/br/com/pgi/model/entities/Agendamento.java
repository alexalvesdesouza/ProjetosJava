package br.com.pgi.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the agendamentos database table.
 * 
 */
@Entity
@Table(name = "agendamentos")
public class Agendamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idAgendamentos;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAgendamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date horarioFimAgendamento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date horarioInicioAgendamento;

	@Column(length = 300)
	private String observacaoAgendamento;

	// bi-directional many-to-one association to Cliente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCliente", nullable = false)
	private Cliente cliente;

	// bi-directional many-to-one association to Operadordosistema
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idOperador", nullable = false)
	private Operadordosistema operadordosistema;

	public Agendamento() {
	}

	public int getIdAgendamentos() {
		return this.idAgendamentos;
	}

	public void setIdAgendamentos(int idAgendamentos) {
		this.idAgendamentos = idAgendamentos;
	}

	public Date getDataAgendamento() {
		return this.dataAgendamento;
	}

	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public Date getHorarioFimAgendamento() {
		return this.horarioFimAgendamento;
	}

	public void setHorarioFimAgendamento(Date horarioFimAgendamento) {
		this.horarioFimAgendamento = horarioFimAgendamento;
	}

	public Date getHorarioInicioAgendamento() {
		return this.horarioInicioAgendamento;
	}

	public void setHorarioInicioAgendamento(Date horarioInicioAgendamento) {
		this.horarioInicioAgendamento = horarioInicioAgendamento;
	}

	public String getObservacaoAgendamento() {
		return this.observacaoAgendamento;
	}

	public void setObservacaoAgendamento(String observacaoAgendamento) {
		this.observacaoAgendamento = observacaoAgendamento;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Operadordosistema getOperadordosistema() {
		return this.operadordosistema;
	}

	public void setOperadordosistema(Operadordosistema operadordosistema) {
		this.operadordosistema = operadordosistema;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataAgendamento == null) ? 0 : dataAgendamento.hashCode());
		result = prime
				* result
				+ ((horarioFimAgendamento == null) ? 0 : horarioFimAgendamento
						.hashCode());
		result = prime
				* result
				+ ((horarioInicioAgendamento == null) ? 0
						: horarioInicioAgendamento.hashCode());
		result = prime
				* result
				+ ((operadordosistema == null) ? 0 : operadordosistema
						.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agendamento other = (Agendamento) obj;
		if (dataAgendamento == null) {
			if (other.dataAgendamento != null)
				return false;
		} else if (!dataAgendamento.equals(other.dataAgendamento))
			return false;
		if (horarioFimAgendamento == null) {
			if (other.horarioFimAgendamento != null)
				return false;
		} else if (!horarioFimAgendamento.equals(other.horarioFimAgendamento))
			return false;
		if (horarioInicioAgendamento == null) {
			if (other.horarioInicioAgendamento != null)
				return false;
		} else if (!horarioInicioAgendamento
				.equals(other.horarioInicioAgendamento))
			return false;
		if (operadordosistema == null) {
			if (other.operadordosistema != null)
				return false;
		} else if (!operadordosistema.equals(other.operadordosistema))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Agendamento [idAgendamentos=" + idAgendamentos
				+ ", dataAgendamento=" + dataAgendamento
				+ ", horarioFimAgendamento=" + horarioFimAgendamento
				+ ", horarioInicioAgendamento=" + horarioInicioAgendamento
				+ ", observacaoAgendamento=" + observacaoAgendamento
				+ ", cliente=" + cliente + ", operadordosistema="
				+ operadordosistema + "]";
	}

}