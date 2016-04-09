package br.com.pgi.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.pgi.model.dao.PromocaoDao;
import br.com.pgi.model.entities.Promocao;
import br.com.pgi.model.util.JPAUtil;
import br.com.pgi.model.util.UtilErros;
import br.com.pgi.model.util.UtilMenssagens;

public class PromocaoDaoImpl implements PromocaoDao {

	private Promocao promocao;
	private List<Promocao> promocoes;
	private EntityManager em;

	public PromocaoDaoImpl() {
		em = JPAUtil.getEntityManager();
	}

	public boolean gravarPromocao(Promocao obj) {
		//EntityManager em = JPAUtil.getEntityManager();
		try {
			em.getTransaction().begin();
			if (obj.getIdPromocao() == 0) {
				em.persist(obj);
				UtilMenssagens.mensagemInformacao("Promoção "
						+ obj.getNomePromocao() + " Cadastrada com Sucesso!!");
			} else {
				em.merge(obj);
				UtilMenssagens.mensagemInformacao("Promoção "
						+ obj.getNomePromocao() + " Alterada com Sucesso!!");
			}
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			em.getTransaction().rollback();
			UtilMenssagens.mensagemErro("Erro ao persistir a Promoção. "
					+ UtilErros.getMensagemErro(e));
		}
		return false;
	}

	public boolean excluirPromocao(Promocao obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<Promocao> listaPromocoes() {
		// TODO Auto-generated method stub
		//EntityManager em = JPAUtil.getEntityManager();
		if (this.promocoes == null) {
			Query q = em
					.createQuery("select a from Promocao a", Promocao.class);
			this.promocoes = q.getResultList();
			em.close();
		}
		return promocoes;
	}

	public Promocao localizarPromocaoPoId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the promocoes
	 */
	public List<Promocao> getPromocoes() {
		return promocoes;
	}

}
