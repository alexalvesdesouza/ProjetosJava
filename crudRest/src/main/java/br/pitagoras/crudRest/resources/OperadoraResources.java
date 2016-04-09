package br.pitagoras.crudRest.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.pitagoras.crudRest.dao.OperadorasDAO;
import br.pitagoras.crudRest.dao.impl.OperadoraImpl;
import br.pitagoras.crudRest.entities.OperadoraDTO;

@Path("operadoras")
public class OperadoraResources {

	private OperadorasDAO dao = null;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<OperadoraDTO> getOperadoras() {
		dao = new OperadoraImpl();
		return dao.listaDeOperadoras();
	}

}
