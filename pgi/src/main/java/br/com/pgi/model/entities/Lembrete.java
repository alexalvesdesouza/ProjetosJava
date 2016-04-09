package br.com.pgi.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the lembrete database table.
 * 
 */
@Entity
@Table(name = "lembrete")
public class Lembrete implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idLembrete;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataLembrete;

	@Column(nullable = false)
	private int idOperadorEnvia;

	@Column(nullable = false, length = 50)
	private String textoLembrete;

	// bi-directional many-to-one association to Operadordosistema
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idOperadorRecebe", nullable = false)
	private Operadordosistema operadordosistema;

	public Lembrete() {
	}

	public int getIdLembrete() {
		return this.idLembrete;
	}

	public void setIdLembrete(int idLembrete) {
		this.idLembrete = idLembrete;
	}

	public Date getDataLembrete() {
		return this.dataLembrete;
	}

	public void setDataLembrete(Date dataLembrete) {
		this.dataLembrete = dataLembrete;
	}

	public int getIdOperadorEnvia() {
		return this.idOperadorEnvia;
	}

	public void setIdOperadorEnvia(int idOperadorEnvia) {
		this.idOperadorEnvia = idOperadorEnvia;
	}

	public String getTextoLembrete() {
		return this.textoLembrete;
	}

	public void setTextoLembrete(String textoLembrete) {
		this.textoLembrete = textoLembrete;
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
				+ ((textoLembrete == null) ? 0 : textoLembrete.hashCode());
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
		Lembrete other = (Lembrete) obj;
		if (textoLembrete == null) {
			if (other.textoLembrete != null)
				return false;
		} else if (!textoLembrete.equals(other.textoLembrete))
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
		return "Lembrete [idLembrete=" + idLembrete + ", dataLembrete="
				+ dataLembrete + ", idOperadorEnvia=" + idOperadorEnvia
				+ ", textoLembrete=" + textoLembrete + ", operadordosistema="
				+ operadordosistema + "]";
	}

}