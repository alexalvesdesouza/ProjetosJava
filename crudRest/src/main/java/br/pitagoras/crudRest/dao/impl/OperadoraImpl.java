package br.pitagoras.crudRest.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.pitagoras.crudRest.dao.OperadorasDAO;
import br.pitagoras.crudRest.entities.OperadoraDTO;
import br.pitagoras.crudRest.persistence.JPAUtil;

public class OperadoraImpl implements OperadorasDAO {

	private EntityManager em = null;

	public OperadoraDTO cadastraOperadora(OperadoraDTO operadora) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OperadoraDTO> listaDeOperadoras() {
		this.em = JPAUtil.getEntityManager();
		@SuppressWarnings("unchecked")
		TypedQuery<OperadoraDTO> query = (TypedQuery<OperadoraDTO>) em.createNamedQuery("OperadoraDTO.findAll");
		return query.getResultList();
	}

	public OperadoraDTO deletaOperadora(OperadoraDTO operadora) {
		// TODO Auto-generated method stub
		return null;
	}

	public OperadoraDTO alteraOperadora(OperadoraDTO operadora) {
		// TODO Auto-generated method stub
		return null;
	}

	public OperadoraDTO getOperadora(Integer codigo) {
		// TODO Auto-generated method stub
		this.em = JPAUtil.getEntityManager();
		return this.em.find(OperadoraDTO.class, codigo);
	}

}
