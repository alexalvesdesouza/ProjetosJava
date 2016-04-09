package br.com.pgi.model.dao;

import java.util.List;

import br.com.pgi.model.entities.Cliente;

public interface ClienteDao {
	
	public boolean gravarCliente(Cliente obj); 
	
	public List<Cliente> getClientes();
	
	public List<Cliente> listaClientes();
	
	public List<Cliente> complete(String busca);
	
	public boolean excluirCliente(Cliente obj);
	
	public Cliente localizar(int id);
	
	
	
	

}
