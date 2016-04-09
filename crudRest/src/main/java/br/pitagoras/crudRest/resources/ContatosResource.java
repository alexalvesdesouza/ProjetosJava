package br.pitagoras.crudRest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.pitagoras.crudRest.dao.ContatosDAO;
import br.pitagoras.crudRest.dao.impl.ContatoImpl;
import br.pitagoras.crudRest.entities.ContatoDTO;

@Path("contatos")
public class ContatosResource {

	private ContatosDAO dao = null;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ContatoDTO> getContatos() {
		dao = new ContatoImpl();
		List<ContatoDTO> contatosList = new ArrayList<ContatoDTO>();
		contatosList = dao.listaContatos();
		return contatosList;
	}

	@Path("{matricula}")
	@GET
	@Produces("text/xml")
	public ContatoDTO buscaContato(@PathParam("matricula") Integer matricula) {
		dao = new ContatoImpl();
		return dao.getContato(matricula);
	}

	@POST
	@Consumes("application/json")
	@Produces("text/plain")
	public String adicionaAluno(ContatoDTO contato) {
		dao = new ContatoImpl();
		ContatoDTO novoContato = dao.cadastraContato(contato);
		return novoContato.getNome() + " adicionado.";

	}

	@Path("{matricula}")
	@PUT
	@Consumes("text/xml")
	@Produces("text/plain")
	public String atualizaAluno(ContatoDTO aluno, @PathParam("matricula") Integer matricula) {
		dao = new ContatoImpl();
		ContatoDTO atual = dao.getContato(matricula);
		dao.alteraContato(atual);
		atual.setNome(aluno.getNome());
		return aluno.getNome() + " atualizado com sucesso";

	}

	@Path("{matricula}")
	@DELETE
	@Produces("text/plain")
	public String removeAluno(@PathParam("matricula") Integer matricula) {
		dao = new ContatoImpl();
		ContatoDTO removido = dao.deletaContato(dao.getContato(matricula));
		StringBuilder retorno = new StringBuilder().append(removido.getNome()).append(" removido com sucesso!");
		return retorno.toString();

	}

}
