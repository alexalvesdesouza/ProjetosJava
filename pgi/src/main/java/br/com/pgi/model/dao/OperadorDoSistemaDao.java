package br.com.pgi.model.dao;

import java.util.List;

import br.com.pgi.model.entities.Operadordosistema;

public interface OperadorDoSistemaDao {

	public boolean gravarOperadorDoSistema(Operadordosistema obj);

	public boolean excluirOperadorDoSistema(Operadordosistema obj);

	public boolean login(String usuario, String senha);

	public boolean trocarSenha(Operadordosistema obj);

	public List<Operadordosistema> complete(String nome);

	public List<Operadordosistema> getOperadores();

	public Operadordosistema localizarOperadorPorUsername(String usuario);

	public Operadordosistema localizaPorNome(String nome);

	public Operadordosistema localizarOperadorDoSistema(int id);

	public String bloquearUsuario(Operadordosistema user,
			boolean agendaPorEmail, String motivoBloqueio);

}
