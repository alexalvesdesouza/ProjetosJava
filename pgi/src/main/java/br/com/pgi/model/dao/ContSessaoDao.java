package br.com.pgi.model.dao;

import java.util.List;

import br.com.pgi.model.entities.ContSessao;

public interface ContSessaoDao {

	public boolean gravarContSessao(ContSessao obj);

	public boolean excluirContSessao(ContSessao obj);

	public ContSessao localizarContSessaoPorId(int id);

	public List<ContSessao> listaContSessoes();

}
