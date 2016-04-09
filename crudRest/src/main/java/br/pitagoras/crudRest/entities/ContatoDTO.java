package br.pitagoras.crudRest.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.bind.CycleRecoverable;

import java.util.Date;


/**
 * The persistent class for the tab_contato database table.
 * 
 */
@Entity
@Table(name="tab_contato")
@NamedQuery(name="ContatoDTO.findAll", query="SELECT t FROM ContatoDTO t")
@XmlRootElement
public class ContatoDTO implements Serializable, CycleRecoverable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_CONTATO_CODCONTATO_GENERATOR", sequenceName="SEQ_PK_COD_CONTATO")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CONTATO_CODCONTATO_GENERATOR")
	@Column(name="cod_contato")
	private Integer codContato;

	@Column(name="des_cpf")
	private String desCpf;

	@Column(name="des_email")
	private String desEmail;

	@Column(name="des_matricula")
	private String matricula;

	@Column(name="des_sexo")
	private String desSexo;

	@Column(name="des_telefone")
	private String telefone;

	@Temporal(TemporalType.DATE)
	@Column(name="dta_registro")
	private Date data;

	@Column(name="nom_contato")
	private String nome;

	//bi-directional many-to-one association to TabOperadora
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fk_cod_operadora")
	private OperadoraDTO operadora;

	public ContatoDTO() {
	}

	public Integer getCodContato() {
		return codContato;
	}

	public void setCodContato(Integer codContato) {
		this.codContato = codContato;
	}

	public String getDesCpf() {
		return desCpf;
	}

	public void setDesCpf(String desCpf) {
		this.desCpf = desCpf;
	}

	public String getDesEmail() {
		return desEmail;
	}

	public void setDesEmail(String desEmail) {
		this.desEmail = desEmail;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDesSexo() {
		return desSexo;
	}

	public void setDesSexo(String desSexo) {
		this.desSexo = desSexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public OperadoraDTO getOperadora() {
		return operadora;
	}

	public void setOperadora(OperadoraDTO operadora) {
		this.operadora = operadora;
	}

	@Override
	public String toString() {
		return "contato [codContato=" + codContato + ", desCpf=" + desCpf + ", desEmail=" + desEmail + ", matricula="
				+ matricula + ", desSexo=" + desSexo + ", telefone=" + telefone + ", data=" + data + ", nome=" + nome
				+ ", operadora=" + operadora.getNomOperadora() + ", cor=" + operadora.getCor() + ", codigoOperaora="+ operadora.getCodOperadora() + "]";
	}

	public Object onCycleDetected(Context context) {
		// TODO Auto-generated method stub
		ContatoDTO contato = new ContatoDTO();
		contato.setCodContato(this.getCodContato());
		return contato;
	}

}