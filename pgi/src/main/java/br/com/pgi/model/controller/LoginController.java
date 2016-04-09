package br.com.pgi.model.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import br.com.pgi.model.dao.impl.OperadorDoSistemaDaoImpl;
import br.com.pgi.model.entities.Operadordosistema;
import br.com.pgi.model.util.ResetaSenha;
import br.com.pgi.model.util.UtilMenssagens;


@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OperadorDoSistemaDaoImpl dao;
	private Operadordosistema usuarioLogado;
	private String usuario;
	private String senha;
	private ResetaSenha rs = new ResetaSenha();
	private String ns;
	private HttpSession session;

	public LoginController() {
		dao = new OperadorDoSistemaDaoImpl();
	}

	public String paginaLogin() {
		return "/login";
	}

	public String efetuarLogin() {

		ns = rs.convertStringToMd5(senha);
		if (dao.login(usuario, ns)) {
			usuarioLogado = dao.localizarOperadorPorUsername(usuario);
			UtilMenssagens.mensagemInformacao("Bem Vindo: "
					+ usuarioLogado.getNomeOperador() + " !!!");
			return "/index";
		} else {
			UtilMenssagens
					.mensagemErro("Login não efetuado -- Usuario ou senha inválidos!");

			return "/login";
		}
	}

	public String efetuarLogout() {
		usuarioLogado = null;
		return "/login?faces-redirect=true";
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public HttpSession getSession() {
		return (HttpSession) getFacesContext().getExternalContext().getSession(
				false);
	}

	public void setSession(HttpSession session) {
		this.session.setAttribute("userLogado", usuarioLogado);
	}

	// public HttpSession getSession() {
	// return (HttpSession)
	// getFacesContext().getExternalContext().getSession(false);
	// }

	public OperadorDoSistemaDaoImpl getDao() {
		return dao;
	}

	public void setDao(OperadorDoSistemaDaoImpl dao) {
		this.dao = dao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Operadordosistema getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Operadordosistema usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public ResetaSenha getRs() {
		return rs;
	}

	public void setRs(ResetaSenha rs) {
		this.rs = rs;
	}

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

}
