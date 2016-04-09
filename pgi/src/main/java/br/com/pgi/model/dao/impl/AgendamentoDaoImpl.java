package br.com.pgi.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.pgi.model.dao.AgendamentoDao;
import br.com.pgi.model.entities.Agendamento;
import br.com.pgi.model.util.JPAUtil;
import br.com.pgi.model.util.UtilErros;
import br.com.pgi.model.util.UtilMenssagens;

public class AgendamentoDaoImpl implements AgendamentoDao {

	private EntityManager em;
	private Agendamento agendamento;
	private List<Agendamento> agendamentos;
	private List<Agendamento> agendamentosId;
	private List<Agendamento> minhaAgendaList;
	private int id;
	private OperadorDoSistemaDaoImpl operadorDao;

	public AgendamentoDaoImpl() {
		this.em = JPAUtil.getEntityManager();
	}

	public boolean gravarAgendamento(Agendamento obj) {
		// TODO Auto-generated method stubz
		try {
			em.getTransaction().begin();
			em.merge(obj);
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();
			UtilMenssagens.mensagemErro("Erro ao persistir o Agendamento. "
					+ UtilErros.getMensagemErro(e));
		}
		return false;
	}

	public boolean atualizarAgendamento(Agendamento age) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Agendamento> getAgendamentos() {
		// TODO Auto-generated method stub
		Query q = em.createQuery("select a from Agendamento a",
				Agendamento.class);
		this.agendamentos = q.getResultList();

		return agendamentos;
	}

	public List<Agendamento> getMinhaAgendaList() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean excluirAgendamento(Agendamento obj) {
		// TODO Auto-generated method stub
		agendamento = new Agendamento();
		try {
			em.getTransaction().begin();
			agendamento = em.merge(obj);
			em.remove(agendamento);
			em.getTransaction().commit();
			UtilMenssagens.mensagemInformacao("Agendamento ID"
					+ obj.getIdAgendamentos() + " Cancelado com Sucesso!!");
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();
			UtilMenssagens.mensagemErro("Erro ao Cancelar o Agendamento. "
					+ UtilErros.getMensagemErro(e));
		}
		return false;
	}

	public Agendamento localizarAgendamento(int id) {
		// TODO Auto-generated method stub
		return em.find(Agendamento.class, id);
	}

	/**
	 * @return the agendamento
	 */
	public Agendamento getAgendamento() {
		return agendamento;
	}

	/**
	 * @return the operadorDao
	 */
	public OperadorDoSistemaDaoImpl getOperadorDao() {
		return operadorDao;
	}

}
