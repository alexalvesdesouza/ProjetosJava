package br.com.pgi.model.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the promocao database table.
 * 
 */
@Entity
@Table(name = "promocao")
public class Promocao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idPromocao;

	@Column(length = 100)
	private String nomePromocao;

	@Column(nullable = false)
	private int quantidade;

	// bi-directional many-to-one association to ContSessao
	@OneToMany(mappedBy = "promocao")
	private List<ContSessao> contSessaos;

	public Promocao() {
	}

	public int getIdPromocao() {
		return this.idPromocao;
	}

	public void setIdPromocao(int idPromocao) {
		this.idPromocao = idPromocao;
	}

	public String getNomePromocao() {
		return this.nomePromocao;
	}

	public void setNomePromocao(String nomePromocao) {
		this.nomePromocao = nomePromocao;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public List<ContSessao> getContSessaos() {
		return this.contSessaos;
	}

	public void setContSessaos(List<ContSessao> contSessaos) {
		this.contSessaos = contSessaos;
	}

	public ContSessao addContSessao(ContSessao contSessao) {
		getContSessaos().add(contSessao);
		contSessao.setPromocao(this);

		return contSessao;
	}

	public ContSessao removeContSessao(ContSessao contSessao) {
		getContSessaos().remove(contSessao);
		contSessao.setPromocao(null);

		return contSessao;
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
		result = prime * result + idPromocao;
		result = prime * result
				+ ((nomePromocao == null) ? 0 : nomePromocao.hashCode());
		result = prime * result + quantidade;
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
		Promocao other = (Promocao) obj;
		if (idPromocao != other.idPromocao)
			return false;
		if (nomePromocao == null) {
			if (other.nomePromocao != null)
				return false;
		} else if (!nomePromocao.equals(other.nomePromocao))
			return false;
		if (quantidade != other.quantidade)
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
		return this.getNomePromocao();
	}

}