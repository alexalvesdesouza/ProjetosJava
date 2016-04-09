package br.com.pgi.model.dao;

import java.util.List;

import br.com.pgi.model.entities.Promocao;

public interface PromocaoDao {

	public boolean gravarPromocao(Promocao obj);

	public boolean excluirPromocao(Promocao obj);

	public List<Promocao> listaPromocoes();
	
	public List<Promocao> getPromocoes();

	public Promocao localizarPromocaoPoId(int id);

}
