package br.com.pgi.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.pgi.model.dao.ClienteDao;
import br.com.pgi.model.entities.Cliente;
import br.com.pgi.model.util.JPAUtil;
import br.com.pgi.model.util.UtilErros;
import br.com.pgi.model.util.UtilMenssagens;

public class ClienteDaoImpl implements ClienteDao {

	private Cliente cliente = new Cliente();
	private List<Cliente> clientes;
	private EntityManager em;

	public ClienteDaoImpl() {
		em = JPAUtil.getEntityManager();
	}

	public boolean gravarCliente(Cliente obj) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			if (obj.getIdCliente() == 0) {
				em.persist(obj);
				UtilMenssagens.mensagemInformacao("Cliente "
						+ obj.getNomeCliente() + " Cadastrado com Sucesso!!");
			} else {
				em.merge(obj);
				UtilMenssagens.mensagemInformacao("Cliente "
						+ obj.getNomeCliente() + " Alterado com Sucesso!!");
			}
			em.getTransaction().commit();

			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();

			UtilMenssagens.mensagemErro("Erro ao persistir o cliente. "
					+ UtilErros.getMensagemErro(e));
		}
		return false;
	}

	public List<Cliente> listaClientes() {
		// TODO Auto-generated method stub
		Query qr = this.em
				.createQuery("select c from Cliente c order by c.nomeCliente",
						Cliente.class);
		this.clientes = qr.getResultList();

		return this.clientes;

	}

	public List<Cliente> complete(String busca) {
		// TODO Auto-generated method stub
		List<Cliente> results = new ArrayList<Cliente>();
		if (this.clientes == null) {
			Query q = em.createQuery("select a from Cliente a", Cliente.class);
			this.clientes = q.getResultList();
			em.close();
		}

		for (Cliente clienteBusca : clientes) {
			if (clienteBusca.getNomeCliente().toUpperCase()
					.contains(busca.toUpperCase()))
				results.add(clienteBusca);
		}
		return results;
	}

	public boolean excluirCliente(Cliente obj) {
		// TODO Auto-generated method stub
		try {
			em.getTransaction().begin();
			cliente = em.merge(obj);
			em.remove(cliente);
			em.getTransaction().commit();
			UtilMenssagens.mensagemInformacao("Cliente " + obj.getNomeCliente()
					+ " Removido com Sucesso!!");
			return true;
		} catch (Exception e) {
			if (em.getTransaction().isActive() == false) {
				em.getTransaction().begin();
			}
			em.getTransaction().rollback();
			UtilMenssagens.mensagemErro("Erro ao remover o Cliente. "
					+ UtilErros.getMensagemErro(e));
		}
		return false;
	}

	public Cliente localizar(int id) {
		// TODO Auto-generated method stub
		return this.em.find(Cliente.class, id);
	}

	public List<Cliente> getClientes() {
		// TODO Auto-generated method stub
		return clientes;
	}

}
