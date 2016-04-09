package br.com.pgi.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the operadordosistema database table.
 * 
 */
@Entity
@Table(name = "operadordosistema")
public class Operadordosistema implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idOperador;

	@Column(nullable = false, length = 100)
	private String emailOperador;

	@Column(nullable = false, length = 100)
	private String nomeOperador;

	@Column(nullable = false, length = 20)
	private String perfilOperador;

	@Column(nullable = false)
	private boolean resetaSenha;

	@Column(nullable = false, length = 100)
	private String senhaOperador;

	@Column(nullable = false)
	private boolean statusOperador;

	@Column(nullable = false, length = 50)
	private String usuarioOperador;

	// bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy = "operadordosistema")
	private List<Agendamento> agendamentos;

	// bi-directional many-to-one association to Lembrete
	@OneToMany(mappedBy = "operadordosistema")
	private List<Lembrete> lembretes;

	public Operadordosistema() {
	}

	public int getIdOperador() {
		return this.idOperador;
	}

	public void setIdOperador(int idOperador) {
		this.idOperador = idOperador;
	}

	public String getEmailOperador() {
		return this.emailOperador;
	}

	public void setEmailOperador(String emailOperador) {
		this.emailOperador = emailOperador;
	}

	public String getNomeOperador() {
		return this.nomeOperador;
	}

	public void setNomeOperador(String nomeOperador) {
		this.nomeOperador = nomeOperador;
	}

	public String getPerfilOperador() {
		return this.perfilOperador;
	}

	public void setPerfilOperador(String perfilOperador) {
		this.perfilOperador = perfilOperador;
	}

	public boolean getResetaSenha() {
		return this.resetaSenha;
	}

	public void setResetaSenha(boolean resetaSenha) {
		this.resetaSenha = resetaSenha;
	}

	public String getSenhaOperador() {
		return this.senhaOperador;
	}

	public void setSenhaOperador(String senhaOperador) {
		this.senhaOperador = senhaOperador;
	}

	public boolean getStatusOperador() {
		return this.statusOperador;
	}

	public void setStatusOperador(boolean statusOperador) {
		this.statusOperador = statusOperador;
	}

	public String getUsuarioOperador() {
		return this.usuarioOperador;
	}

	public void setUsuarioOperador(String usuarioOperador) {
		this.usuarioOperador = usuarioOperador;
	}

	public List<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Agendamento addAgendamento(Agendamento agendamento) {
		getAgendamentos().add(agendamento);
		agendamento.setOperadordosistema(this);

		return agendamento;
	}

	public Agendamento removeAgendamento(Agendamento agendamento) {
		getAgendamentos().remove(agendamento);
		agendamento.setOperadordosistema(null);

		return agendamento;
	}

	public List<Lembrete> getLembretes() {
		return this.lembretes;
	}

	public void setLembretes(List<Lembrete> lembretes) {
		this.lembretes = lembretes;
	}

	public Lembrete addLembrete(Lembrete lembrete) {
		getLembretes().add(lembrete);
		lembrete.setOperadordosistema(this);

		return lembrete;
	}

	public Lembrete removeLembrete(Lembrete lembrete) {
		getLembretes().remove(lembrete);
		lembrete.setOperadordosistema(null);

		return lembrete;
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
				+ ((nomeOperador == null) ? 0 : nomeOperador.hashCode());
		result = prime * result
				+ ((usuarioOperador == null) ? 0 : usuarioOperador.hashCode());
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
		Operadordosistema other = (Operadordosistema) obj;
		if (nomeOperador == null) {
			if (other.nomeOperador != null)
				return false;
		} else if (!nomeOperador.equals(other.nomeOperador))
			return false;
		if (usuarioOperador == null) {
			if (other.usuarioOperador != null)
				return false;
		} else if (!usuarioOperador.equals(other.usuarioOperador))
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
		return nomeOperador;
	}

}