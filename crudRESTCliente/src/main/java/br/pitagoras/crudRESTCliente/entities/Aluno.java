package br.pitagoras.crudRESTCliente.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Aluno {

	private Integer matricula;

	private String nome;

	/**
	 * Método Construtor Default
	 */
	public Aluno() {

	}

	/**
	 * Construtor com sobrecarga, para montar o Objeto Aluno
	 * 
	 * @param matricula
	 * @param nome
	 */
	public Aluno(Integer matricula, String nome) {
		super();
		this.matricula = matricula;
		this.nome = nome;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", nome=" + nome + "]";
	}

}
