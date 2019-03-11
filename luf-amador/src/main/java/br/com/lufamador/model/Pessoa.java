package br.com.lufamador.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.lufamador.lufapi.repository.listener.PessoaListener;

@EntityListeners(PessoaListener.class)
@Entity
@Table(name = "pessoa")
@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", allocationSize = 1)
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
    private Long codigo;

    private String nome;

    @Column(name = "TIPO_PESSOA")
    private String tipoPessoa;

    private String documento;

    private Boolean funcionario;

    private Boolean ativa;

    private Boolean arbitro;

    private String funcao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "pessoa_contato", joinColumns = @JoinColumn(name = "codigo_pessoa")
            , inverseJoinColumns = @JoinColumn(name = "codigo_contato"))
    private List<Contato> contatos;

    @Embedded
    private Endereco endereco;

    private String anexo;

    @Transient
    private String urlAnexo;

    public Boolean getArbitro() {
        return arbitro;
    }

    public void setArbitro(Boolean arbitro) {
        this.arbitro = arbitro;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public String getUrlAnexo() {
        return urlAnexo;
    }

    public void setUrlAnexo(String urlAnexo) {
        this.urlAnexo = urlAnexo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getDocumento() {
        return documento;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Boolean getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Boolean funcionario) {
        this.funcionario = funcionario;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(codigo, pessoa.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", tipoPessoa='" + tipoPessoa + '\'' +
                ", documento='" + documento + '\'' +
                ", funcionario=" + funcionario +
                ", contatos=" + contatos +
                ", endereco=" + endereco +
                '}';
    }
}
