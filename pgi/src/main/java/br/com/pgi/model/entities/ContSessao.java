package br.com.pgi.model.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the cont_sessao database table.
 * 
 */
@Entity
@Table(name = "cont_sessao")
public class ContSessao implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContSessaoPK id;

	@Column(length = 200)
	private String observacao;

	@Column(nullable = false)
	private int quantidade;

	// bi-directional many-to-one association to Cliente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idClienteSessao", nullable = false, insertable = false, updatable = false)
	private Cliente cliente;

	// bi-directional many-to-one association to Promocao
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPromocaoSessao", nullable = false, insertable = false, updatable = false)
	private Promocao promocao;

	public ContSessao() {
	}

	public ContSessaoPK getId() {
		return this.id;
	}

	public void setId(ContSessaoPK id) {
		this.id = id;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Promocao getPromocao() {
		return this.promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
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
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result
				+ ((promocao == null) ? 0 : promocao.hashCode());
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
		ContSessao other = (ContSessao) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (promocao == null) {
			if (other.promocao != null)
				return false;
		} else if (!promocao.equals(other.promocao))
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
		return "ContSessao [id=" + id + ", observacao=" + observacao
				+ ", quantidade=" + quantidade + ", cliente=" + cliente
				+ ", promocao=" + promocao + "]";
	}

}