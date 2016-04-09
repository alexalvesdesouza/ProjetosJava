package br.pitagoras.crudRest.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.bind.CycleRecoverable;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the tab_operadora database table.
 * 
 */
@Entity
@Table(name = "tab_operadora")
@NamedQuery(name = "OperadoraDTO.findAll", query = "SELECT t FROM OperadoraDTO t")
@XmlRootElement
public class OperadoraDTO implements Serializable, CycleRecoverable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "TAB_OPERADORA_CODOPERADORA_GENERATOR", sequenceName = "SEQ_PK_COD_OPERADORA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAB_OPERADORA_CODOPERADORA_GENERATOR")
	@Column(name = "cod_operadora")
	private Integer codOperadora;

	@Column(name = "des_codigo")
	private String desCodigo;

	@Column(name = "des_segmento")
	private String desSegmento;

	@Column(name = "nom_operadora")
	private String nomOperadora;

	@Column(name = "val_valor_minuto")
	private BigDecimal valValorMinuto;

	@Column(name = "des_cor")
	private String cor;

	// bi-directional many-to-one association to TabContato
	@OneToMany(mappedBy = "operadora")
	private List<ContatoDTO> tabContatos;

	public OperadoraDTO() {
	}

	public Integer getCodOperadora() {
		return this.codOperadora;
	}

	public void setCodOperadora(Integer codOperadora) {
		this.codOperadora = codOperadora;
	}

	public String getDesCodigo() {
		return this.desCodigo;
	}

	public void setDesCodigo(String desCodigo) {
		this.desCodigo = desCodigo;
	}

	public String getDesSegmento() {
		return this.desSegmento;
	}

	public void setDesSegmento(String desSegmento) {
		this.desSegmento = desSegmento;
	}

	public String getNomOperadora() {
		return this.nomOperadora;
	}

	public void setNomOperadora(String nomOperadora) {
		this.nomOperadora = nomOperadora;
	}

	public BigDecimal getValValorMinuto() {
		return this.valValorMinuto;
	}

	public void setValValorMinuto(BigDecimal valValorMinuto) {
		this.valValorMinuto = valValorMinuto;
	}

	public List<ContatoDTO> getTabContatos() {
		return this.tabContatos;
	}

	public void setTabContatos(List<ContatoDTO> tabContatos) {
		this.tabContatos = tabContatos;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public ContatoDTO addTabContato(ContatoDTO tabContato) {
		getTabContatos().add(tabContato);
		tabContato.setOperadora(this);

		return tabContato;
	}

	public ContatoDTO removeTabContato(ContatoDTO tabContato) {
		getTabContatos().remove(tabContato);
		tabContato.setOperadora(null);

		return tabContato;
	}

	@Override
	public String toString() {
		return "operadora [codOperadora=" + codOperadora + ", desCodigo=" + desCodigo + ", desSegmento=" + desSegmento
				+ ", nomOperadora=" + nomOperadora + ", valValorMinuto=" + valValorMinuto + ", cor=" + cor
				+ ", tabContatos=" + tabContatos + "]";
	}

	public Object onCycleDetected(Context context) {
		OperadoraDTO operadora = new OperadoraDTO();
		operadora.setCodOperadora(this.getCodOperadora());
		return operadora;
	}

}