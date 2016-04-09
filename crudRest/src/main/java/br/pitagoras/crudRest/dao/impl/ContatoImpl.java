package br.pitagoras.crudRest.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.pitagoras.crudRest.dao.ContatosDAO;
import br.pitagoras.crudRest.dao.OperadorasDAO;
import br.pitagoras.crudRest.entities.ContatoDTO;
import br.pitagoras.crudRest.entities.OperadoraDTO;
import br.pitagoras.crudRest.persistence.JPAUtil;

public class ContatoImpl implements ContatosDAO {

	private EntityManager em = JPAUtil.getEntityManager();
	private OperadorasDAO operDAO = null;

	public ContatoDTO cadastraContato(ContatoDTO contato) {
		operDAO = new OperadoraImpl();
		OperadoraDTO oper = operDAO.getOperadora(contato.getOperadora().getCodOperadora());
		contato.setOperadora(oper);
		try {
			this.em.getTransaction().begin();
			this.em.merge(contato);
			this.em.getTransaction().commit();
		} catch (Exception e) {

		} finally {
			this.em.close();
		}

		return contato;
	}

	public List<ContatoDTO> listaContatos() {
		@SuppressWarnings("unchecked")
		TypedQuery<ContatoDTO> query = (TypedQuery<ContatoDTO>) this.em.createQuery("select c from ContatoDTO c");
		return query.getResultList();
	}

	public ContatoDTO deletaContato(ContatoDTO contato) {
		// TODO Auto-generated method stub
		try {
			this.em.getTransaction().begin();
			this.em.remove(contato);
			this.em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			this.em.close();
		}
		return contato;
	}

	public ContatoDTO alteraContato(ContatoDTO contato) {
		// TODO Auto-generated method stub
		try {
			this.em.getTransaction().begin();
			this.em.merge(contato);
			this.em.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			this.em.close();
		}
		return contato;
	}

	public ContatoDTO getContato(Integer matricula) {
		// TODO Auto-generated method stub
		return this.em.find(ContatoDTO.class, matricula);
	}

}
