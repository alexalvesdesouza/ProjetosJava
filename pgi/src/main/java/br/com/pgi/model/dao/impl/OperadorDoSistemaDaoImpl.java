package br.com.pgi.model.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.pgi.model.dao.OperadorDoSistemaDao;
import br.com.pgi.model.entities.Agendamento;
import br.com.pgi.model.entities.Operadordosistema;
import br.com.pgi.model.util.JPAUtil;
import br.com.pgi.model.util.JavaMailApp;
import br.com.pgi.model.util.ResetaSenha;
import br.com.pgi.model.util.UtilErros;
import br.com.pgi.model.util.UtilMenssagens;

public class OperadorDoSistemaDaoImpl implements OperadorDoSistemaDao,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6628306898300291174L;
	/**
	 * 
	 */

	private ResetaSenha rss = new ResetaSenha();
	private Operadordosistema operador = new Operadordosistema();
	private List<Operadordosistema> operadores;
	private JavaMailApp jm = new JavaMailApp();
	private EntityManager em;
	String userTemp;

	public OperadorDoSistemaDaoImpl() {
		em = JPAUtil.getEntityManager();
		operadores = new ArrayList<Operadordosistema>();
	}

	public boolean gravarOperadorDoSistema(Operadordosistema obj) {
		// TODO Auto-generated method stub
		String senhaView = obj.getSenhaOperador();
		String conv = rss.convertStringToMd5(senhaView);
		obj.setSenhaOperador(conv);
		try {
			em.getTransaction().begin();
			if (obj.getIdOperador() == 0) {
				em.persist(obj);
				UtilMenssagens.mensagemInformacao("Operador "
						+ obj.getNomeOperador() + " Cadastrado com Sucesso!!");
			} else {
				em.merge(obj);
				UtilMenssagens.mensagemInformacao("Operador "
						+ obj.getNomeOperador() + " Alterado com Sucesso!!");
			}
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();
			UtilMenssagens.mensagemErro("Erro ao persistir o Operador. "
					+ UtilErros.getMensagemErro(e));
		}
		return false;
	}

	public boolean excluirOperadorDoSistema(Operadordosistema obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean login(String usuario, String senha) {
		// TODO Auto-generated method stub
		Query query = em
				.createQuery(
						"select u from Operadordosistema u where u.usuarioOperador = :usuario and u.senhaOperador = :senha and u.statusOperador = true",
						Operadordosistema.class);
		query.setParameter("usuario", usuario);
		query.setParameter("senha", senha);
		if (!query.getResultList().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("static-access")
	public boolean trocarSenha(Operadordosistema obj) {
		// TODO Auto-generated method stub
		String menssagem = obj.getUsuarioOperador()
				+ "\nVocê alterou sua senha com sucesso!\nPara a nova senha: "
				+ obj.getSenhaOperador();
		String assunto = "Alteração de Senha efetuada no sistema OPA!!";
		if (gravarOperadorDoSistema(obj)) {
			jm.enviagmail(assunto, menssagem, obj.getEmailOperador());
			UtilMenssagens.mensagemInformacao(obj.getUsuarioOperador()
					+ " Senha alterada com sucesso!!");
			return true;
		}
		UtilMenssagens.mensagemErro("Ocorreu um Erro ao alterar sua senha!!");
		return false;
	}

	public List<Operadordosistema> complete(String nome) {
		// TODO Auto-generated method stub
		List<Operadordosistema> results = new ArrayList<Operadordosistema>();
		Query q = em.createQuery("select o from Operadordosistema o ",
				Operadordosistema.class);
		this.operadores = q.getResultList();

		for (Operadordosistema operadorBusca : this.operadores) {
			if (operadorBusca.getNomeOperador().toUpperCase()
					.contains(nome.toUpperCase()))
				results.add(operadorBusca);
		}
		return results;
	}

	public List<Operadordosistema> getOperadores() {
		// TODO Auto-generated method stub
		Query query = this.em.createQuery("select o from Operadordosistema o",
				Operadordosistema.class);

		return query.getResultList();
	}

	public Operadordosistema localizarOperadorPorUsername(String usuario) {
		// TODO Auto-generated method stub
		return (Operadordosistema) em
				.createQuery(
						"select o from Operadordosistema o where upper(o.usuarioOperador) =:usuario")
				.setParameter("usuario", usuario.toUpperCase())
				.getSingleResult();
	}

	public Operadordosistema localizaPorNome(String nome) {
		// TODO Auto-generated method stub
		return (Operadordosistema) em
				.createQuery(
						"select o from Operadordosistema o where upper(o.nomeOperador) =:nome")
				.setParameter("nome", nome.toUpperCase()).getSingleResult();

	}

	public Operadordosistema localizarOperadorDoSistema(int id) {
		// TODO Auto-generated method stub
		return this.em.find(Operadordosistema.class, id);
	}

	public String bloquearUsuario(Operadordosistema user,
			boolean agendaPorEmail, String motivoBloqueio) {
		// TODO Auto-generated method stub
		String assunto = "Bloqueio de acesso do Usuario "
				+ user.getNomeOperador();
		String menssagem = "";

		Operadordosistema op = localizaPorNome(user.getNomeOperador());

		if (!op.getStatusOperador()) {
			UtilMenssagens
					.mensagemInformacao("Operação não realizada - Operador "
							+ op.getNomeOperador()
							+ " já se enconta Bloqueado!");
			return "/privado/usuario/listarUsuario?faces-redirect=true";
		} else {

			EntityManager em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			List<Agendamento> agenda = new ArrayList<Agendamento>();
			Query q = em.createQuery("select a from Agendamento a",
					Agendamento.class);
			agenda = q.getResultList();

			Agendamento removeAgendamento = new Agendamento();
			for (Agendamento agendamento : agenda) {
				if (agendamento.getOperadordosistema().getIdOperador() == op
						.getIdOperador()) {

					menssagem += agendamento.toString();
					removeAgendamento = (Agendamento) agendamento;

					Agendamento ee = em.merge(removeAgendamento);
					em.remove(removeAgendamento);
					// agendamentoDao.excluirAgendamento(removeAgendamento);

				}

			}

			op.setStatusOperador(false);
			gravarOperadorDoSistema(op);
			UtilMenssagens
					.mensagemInformacao("Funcionário Bloqueado Com Sucesso!!");
			String next = "Funcionario Foi bloqueado pelo Motivo: "
					+ motivoBloqueio + "\n" + menssagem;
			if (agendaPorEmail) {

				jm.enviagmail(assunto, next, "alexalvesdesouza@outlook.com");

			}
			em.getTransaction().commit();
			this.operador = new Operadordosistema();
			return "/privado/usuario/listarUsuario?faces-redirect=true";
		}

	}

}
