package br.com.pgi.model.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cont_sessao database table.
 * 
 */
@Embeddable
public class ContSessaoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable = false, updatable = false, unique = true, nullable = false)
	private int idClienteSessao;

	@Column(insertable = false, updatable = false, unique = true, nullable = false)
	private int idPromocaoSessao;

	public ContSessaoPK() {
	}

	public int getIdClienteSessao() {
		return this.idClienteSessao;
	}

	public void setIdClienteSessao(int idClienteSessao) {
		this.idClienteSessao = idClienteSessao;
	}

	public int getIdPromocaoSessao() {
		return this.idPromocaoSessao;
	}

	public void setIdPromocaoSessao(int idPromocaoSessao) {
		this.idPromocaoSessao = idPromocaoSessao;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContSessaoPK)) {
			return false;
		}
		ContSessaoPK castOther = (ContSessaoPK) other;
		return (this.idClienteSessao == castOther.idClienteSessao)
				&& (this.idPromocaoSessao == castOther.idPromocaoSessao);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idClienteSessao;
		hash = hash * prime + this.idPromocaoSessao;

		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContSessaoPK [idClienteSessao=" + idClienteSessao
				+ ", idPromocaoSessao=" + idPromocaoSessao + "]";
	}
}