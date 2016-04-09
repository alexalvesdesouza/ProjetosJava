package br.com.pgi.model.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import br.com.pgi.model.dao.OperadorDoSistemaDao;
import br.com.pgi.model.dao.impl.OperadorDoSistemaDaoImpl;
import br.com.pgi.model.entities.Operadordosistema;
import br.com.pgi.model.util.ConverterOperador;
import br.com.pgi.model.util.JavaMailApp;
import br.com.pgi.model.util.ResetaSenha;

@SuppressWarnings("serial")
@ManagedBean(name = "operadorController")
@SessionScoped
public class OperadorDoSistemaController implements Serializable {

	private ResetaSenha rSenha = new ResetaSenha();
	private OperadorDoSistemaDao dao;
	private ConverterOperador converterOperador;
	private Operadordosistema operador = new Operadordosistema();
	private JavaMailApp jm = new JavaMailApp();
	String emailTest = "alexalvesdesouza@outlook.com";
	private List<Operadordosistema> operadores = new ArrayList<Operadordosistema>();
	private String motivoBloqueio;
	private int number;
	private boolean enviarAgendaPorEmail;
	private LoginController controller;

	public OperadorDoSistemaController() {
		dao = new OperadorDoSistemaDaoImpl();
		converterOperador = new ConverterOperador();

	}

	public String listaOperador() {
		return "/privado/usuario/listarUsuario?faces-redirect=true";
	}

	public String novoOperadorDoSistema() {
		operador = new Operadordosistema();
		return "cadastroUsuario?faces-redirect=true";
	}

	public String alterarSenha(Operadordosistema obj) {
		operador = obj;
		return "altSenhaCadUser?faces-redirect=true";
	}

	public String trocarSenha() {
		dao.trocarSenha(this.operador);
		return "/index?faces-redirect=true";
	}

	public String gravarOperadorDoSistema() {

		dao.gravarOperadorDoSistema(this.operador);

		return "cadastroUsuario.xhtml";

	}

	@SuppressWarnings("static-access")
	public String bloqeiaUsuario() {
		dao.bloquearUsuario(this.operador, this.enviarAgendaPorEmail,
				this.motivoBloqueio);
		return "/privado/usuario/listarUsuario?faces-redirect=true";

	}

	@SuppressWarnings("static-access")
	public String newSenhaID() {
		try {
			String assunto = "Recuperação de senha do Sistma OPA!! com JavaMail";
			String ne = rSenha.gerarNovaSenha();
			System.out.println(number);
			System.out.println("SEnha nova " + ne);
			Operadordosistema op1 = dao.localizarOperadorDoSistema(number);
			op1.setSenhaOperador(ne);
			System.out.println("ID Operador --> " + op1.getIdOperador()
					+ " Nome - " + op1.getNomeOperador());
			System.out.println("Email --> " + op1.getEmailOperador());
			dao.gravarOperadorDoSistema(op1);
			String emailFoi = op1.getEmailOperador();
			String menssagem = "Prezado(a) "
					+ op1.getNomeOperador()
					+ "\nSua senha do sistema OPA! foi recuperada com sucesso!! \nPor medida de segurança Altera sua senha em seu pr�ximo acesso.\nNova Senha: "
					+ ne;
			jm.enviagmail(assunto, menssagem, emailFoi);

		} catch (Exception e) {

		}
		return "/index?faces-redirect=true";
	}

	public String cancelar() {
		this.operador = null;
		return "cadastroUsuario?faces-redirect=true";
	}

	public String cancelarRecSenha() {

		return "/login?faces-redirect=true";
	}

	public String alterarOperadorDoSistema(Operadordosistema obj) {
		HttpSession sees = (HttpSession) controller.getSession().getAttribute(
				"userLogado");
		System.out.println("session --> " + sees.toString());
		operador = obj;
		return "cadastroUsuario?faces-redirect=true";
	}

	public String excluirOperadorDoSistema() {
		dao.excluirOperadorDoSistema(operador);
		return "listarUsuario.xhtml";
	}

	public OperadorDoSistemaDao getDao() {
		return dao;
	}

	public Operadordosistema getOperador() {
		return operador;
	}

	public void setOperador(Operadordosistema operador) {
		this.operador = operador;
	}

	public ResetaSenha getrSenha() {
		return rSenha;
	}

	public void setrSenha(ResetaSenha rSenha) {
		this.rSenha = rSenha;
	}

	public ConverterOperador getConverterOperador() {
		return converterOperador;
	}

	public void setConverterOperador(ConverterOperador converterOperador) {
		this.converterOperador = converterOperador;
	}

	public JavaMailApp getJm() {
		return jm;
	}

	public void setJm(JavaMailApp jm) {
		this.jm = jm;
	}

	public String getEmailTest() {
		return emailTest;
	}

	public void setEmailTest(String emailTest) {
		this.emailTest = emailTest;
	}

	public List<Operadordosistema> getOperadores() {
		return operadores;
	}

	public void setOperadores(List<Operadordosistema> operadores) {
		this.operadores = operadores;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isEnviarAgendaPorEmail() {
		return enviarAgendaPorEmail;
	}

	public void setEnviarAgendaPorEmail(boolean enviarAgendaPorEmail) {
		this.enviarAgendaPorEmail = enviarAgendaPorEmail;
	}

	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}

	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}

	public void setUsuario(String usuario) {
		// TODO Auto-generated method stub

	}

}
