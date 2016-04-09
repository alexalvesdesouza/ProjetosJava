package br.com.pgi.model.entities;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.persistence.*;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int idCliente;

	@Column(length = 30)
	private String bairro;

	@Column(nullable = false, length = 15)
	private String celularCliente;

	@Column(length = 11)
	private String cep;

	@Column(length = 50)
	private String cidade;

	@Column(length = 100)
	private String complemento;

	@Temporal(TemporalType.DATE)
	private Date dataNascimentoCliente;

	@Column(length = 100)
	private String emailCliente;

	@Lob
	private byte[] fotoCliente;

	@Column(nullable = false, length = 100)
	private String nomeCliente;

	private int numero;

	@Column(length = 50)
	private String rua;

	@Column(length = 15)
	private String telefoneCliente;

	@Column(length = 2)
	private String uf;

	// bi-directional many-to-one association to Agendamento
	@OneToMany(mappedBy = "cliente")
	private List<Agendamento> agendamentos;

	// bi-directional many-to-one association to ContSessao
	@OneToMany(mappedBy = "cliente")
	private List<ContSessao> contSessaos;

	@Transient
	private StreamedContent imagem;

	public Cliente() {
	}

	public StreamedContent getImagem() {
		if (this.getFotoCliente() != null) {
			return new DefaultStreamedContent(new ByteArrayInputStream(
					this.getFotoCliente()), "");
		} else
			return new DefaultStreamedContent();
	}

	public int getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCelularCliente() {
		return this.celularCliente;
	}

	public void setCelularCliente(String celularCliente) {
		this.celularCliente = celularCliente;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Date getDataNascimentoCliente() {
		return this.dataNascimentoCliente;
	}

	public void setDataNascimentoCliente(Date dataNascimentoCliente) {
		this.dataNascimentoCliente = dataNascimentoCliente;
	}

	public String getEmailCliente() {
		return this.emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public byte[] getFotoCliente() {
		return this.fotoCliente;
	}

	public void setFotoCliente(byte[] fotoCliente) {
		this.fotoCliente = fotoCliente;
	}

	public String getNomeCliente() {
		return this.nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getRua() {
		return this.rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getTelefoneCliente() {
		return this.telefoneCliente;
	}

	public void setTelefoneCliente(String telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	public void setAgendamentos(List<Agendamento> agendamentos) {
		this.agendamentos = agendamentos;
	}

	public Agendamento addAgendamento(Agendamento agendamento) {
		getAgendamentos().add(agendamento);
		agendamento.setCliente(this);

		return agendamento;
	}

	public Agendamento removeAgendamento(Agendamento agendamento) {
		getAgendamentos().remove(agendamento);
		agendamento.setCliente(null);

		return agendamento;
	}

	public List<ContSessao> getContSessaos() {
		return this.contSessaos;
	}

	public void setContSessaos(List<ContSessao> contSessaos) {
		this.contSessaos = contSessaos;
	}

	public ContSessao addContSessao(ContSessao contSessao) {
		getContSessaos().add(contSessao);
		contSessao.setCliente(this);

		return contSessao;
	}

	public ContSessao removeContSessao(ContSessao contSessao) {
		getContSessaos().remove(contSessao);
		contSessao.setCliente(null);

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
		result = prime * result
				+ ((celularCliente == null) ? 0 : celularCliente.hashCode());
		result = prime * result
				+ ((nomeCliente == null) ? 0 : nomeCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (celularCliente == null) {
			if (other.celularCliente != null)
				return false;
		} else if (!celularCliente.equals(other.celularCliente))
			return false;
		if (nomeCliente == null) {
			if (other.nomeCliente != null)
				return false;
		} else if (!nomeCliente.equals(other.nomeCliente))
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
		return this.getNomeCliente();
	}

}