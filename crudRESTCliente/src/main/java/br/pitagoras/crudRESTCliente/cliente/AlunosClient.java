package br.pitagoras.crudRESTCliente.cliente;

import java.util.List;

import br.pitagoras.crudRESTCliente.entities.Aluno;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

public class AlunosClient {

	public List<Aluno> getAlunos() {
		Client client = Client.create();
		WebResource webresource = client.resource("http://localhost:8080/crudRest/rest/alunos");
		ClientResponse response = webresource.accept("text/xml").get(ClientResponse.class);
		List<Aluno> alunos = response.getEntity(new GenericType<List<Aluno>>() {
			
		});

		return alunos;
	}

	public Aluno getAluno(int matricula) {
		Client client = Client.create();
		WebResource webresource = client.resource("http://localhost:8080/crudRest/rest/alunos/"	+ matricula);
		ClientResponse response = webresource.accept("text/xml").get(ClientResponse.class);
		Aluno aluno = response.getEntity(Aluno.class);
		return aluno;
	}

	public String adicionaAluno(Aluno aluno) {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/crudRest/rest/alunos");
		ClientResponse response = webResource.type("text/xml").post(ClientResponse.class, aluno);
		String msg = response.getEntity(String.class);
		return msg;
	}

	public String atualizaAluno(Aluno aluno) {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/crudRest/rest/alunos/"+ aluno.getMatricula());
		ClientResponse response = webResource.type("text/xml").put(ClientResponse.class, aluno);
		String msg = response.getEntity(String.class);
		return msg;
	}

	public String removeAluno(Aluno aluno) {
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/crudRest/rest/alunos/"	+ aluno.getMatricula());
		ClientResponse response = webResource.type("text/xml").delete(ClientResponse.class);
		String msg = response.getEntity(String.class);
		return msg;
	}
}
