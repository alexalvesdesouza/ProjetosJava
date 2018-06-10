package br.com.lufamador.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.lufamador.utils.datas.LocalDateDeserializer;
import br.com.lufamador.utils.datas.LocalDateSerializer;

@Entity
@Table(name = "luf_agremiacao")
public class Agremiacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long codigo;

    private String nome;
    private String nomeSigla;
    private String linkEscudo;
    private Boolean inativa;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAfiliacao;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataMandatoDiretoria;
    private String observacao;

    @OneToOne
    private Endereco endereco;

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

    public String getNomeSigla() {
      return nomeSigla;
    }

    public void setNomeSigla(String nomeSigla) {
      this.nomeSigla = nomeSigla;
    }

    public String getLinkEscudo() {
      return linkEscudo;
    }

    public void setLinkEscudo(String linkEscudo) {
      this.linkEscudo = linkEscudo;
    }

    public Boolean getInativa() {
      return inativa;
    }

    public void setInativa(Boolean inativa) {
      this.inativa = inativa;
    }

    public LocalDate getDataAfiliacao() {
      return dataAfiliacao;
    }

    public void setDataAfiliacao(LocalDate dataAfiliacao) {
      this.dataAfiliacao = dataAfiliacao;
    }

    public LocalDate getDataMandatoDiretoria() {
      return dataMandatoDiretoria;
    }

    public void setDataMandatoDiretoria(LocalDate dataMandatoDiretoria) {
      this.dataMandatoDiretoria = dataMandatoDiretoria;
    }

    public String getObservacao() {
      return observacao;
    }

    public void setObservacao(String observacao) {
      this.observacao = observacao;
    }

    public Endereco getEndereco() {
      return endereco;
    }

    public void setEndereco(Endereco endereco) {
      this.endereco = endereco;
    }

}
