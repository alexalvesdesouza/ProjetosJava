package br.com.pgi.model.dao;

import java.util.List;

import br.com.pgi.model.entities.Agendamento;

public interface AgendamentoDao {
	
	public boolean gravarAgendamento(Agendamento obj);
	
	public boolean atualizarAgendamento(Agendamento age);
	
	public List<Agendamento> getAgendamentos();
	
	public List<Agendamento> getMinhaAgendaList();
	
	public boolean excluirAgendamento(Agendamento obj);
	
	public Agendamento localizarAgendamento(int id);

}
