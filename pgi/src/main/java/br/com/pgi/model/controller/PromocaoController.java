package br.com.pgi.model.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.pgi.model.dao.PromocaoDao;
import br.com.pgi.model.dao.impl.PromocaoDaoImpl;
import br.com.pgi.model.entities.Promocao;

@RequestScoped
@ManagedBean(name = "promocaoController")
public class PromocaoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PromocaoDao dao;
	private Promocao promocao = new Promocao();

	public PromocaoController() {
		dao = new PromocaoDaoImpl();
	}

	public List<Promocao> listaPromocao() {

		return dao.listaPromocoes();
	}

	public String novo() {

		promocao = new Promocao();
		return "cadastrarPromocao?faces-redirect=true";
	}

	public String cancelar() {

		return "listaPromocao.xhtml";
	}

	public String gravarPromocao() {
		dao.gravarPromocao(promocao);
		this.promocao = new Promocao();
		return "cadastrarPromocao.xhtml";
	}

	public String alterarPromocao(Promocao obj) {
		this.promocao = obj;
		return "cadastrarPromocao.xhtml";
	}

	public String excluirPromocao(Promocao obj) {
		dao.excluirPromocao(obj);
		return "listaPromocao.xhtml";
	}

	public Promocao getPromocao() {
		return promocao;
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	/**
	 * @return the dao
	 */
	public PromocaoDao getDao() {
		return dao;
	}

}