package br.pitagoras.crudRest.dao;

import java.util.List;

import br.pitagoras.crudRest.entities.ContatoDTO;

public interface ContatosDAO {

	public ContatoDTO cadastraContato(ContatoDTO contato);

	public List<ContatoDTO> listaContatos();

	public ContatoDTO deletaContato(ContatoDTO contato);

	public ContatoDTO alteraContato(ContatoDTO contato);

	public ContatoDTO getContato(Integer matricula);

}
