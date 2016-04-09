package br.com.pgi.model.dao;

import java.util.List;

import br.com.pgi.model.entities.Lembrete;

public interface LembreteDao {
	
	public boolean gravarLembrete(Lembrete obj);
	
	public List<Lembrete> getLembretes() ;
	
	public boolean excluirLembrete(Lembrete obj);
	
	public Lembrete localizarLembrete(int id);

}
