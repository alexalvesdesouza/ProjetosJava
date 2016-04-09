package br.pitagoras.crudRest.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.pitagoras.crudRest.entities.Aluno;

@Path("alunos")
public class AlunosResource {

	static private Map<Integer, Aluno> alunosMap;

	static {

		alunosMap = new HashMap<Integer, Aluno>();
		Aluno a1 = new Aluno(1, "Alex Alves de Souza");
		Aluno a2 = new Aluno(2, "Laureano Fernandes");
		Aluno a3 = new Aluno(3, "Analice Ferreira");
		Aluno a4 = new Aluno(4, "Paulo Henrique Oliveira");
		Aluno a5 = new Aluno(5, "Michele Santos");

		alunosMap.put(a1.getMatricula(), a1);
		alunosMap.put(a2.getMatricula(), a2);
		alunosMap.put(a3.getMatricula(), a3);
		alunosMap.put(a4.getMatricula(), a4);
		alunosMap.put(a5.getMatricula(), a5);

	}

	@GET
	@Produces("text/xml")
	public List<Aluno> getAlunos() {
		System.out.println("--> Passou aki");
		return new ArrayList<Aluno>(alunosMap.values());
	}

	@Path("{matricula}")
	@GET
	@Produces("text/xml")
	public Aluno getAluno(@PathParam("matricula") Integer matricula) {

		return alunosMap.get(matricula);
	}

	
	/**
	 * Método responsável por inserir novos objetos do tipo Aluno.
	 * @param Aluno
	 * @return String
	 */
	@POST
	@Consumes("text/xml")
	@Produces("text/plain")
	public String adicionaAluno(Aluno aluno) {

		aluno.setMatricula(alunosMap.size() + 1);
		alunosMap.put(aluno.getMatricula(), aluno);
		System.out.println(aluno);
		return aluno.getNome() + " adicionado.";

	}

	
	/**
	 * Método responsável por atualizar os dados do aluno.
	 * @param Aluno - Objeto do tipo aluno.
	 * @param Integer - Passa o ID do aluno.
	 * @return String
	 */
	@Path("{matricula}")
//	@POST
	@PUT
	@Consumes("text/xml")
	@Produces("text/plain")
	public String atualizaAluno(Aluno aluno, @PathParam("matricula") Integer matricula) {

		Aluno atual = alunosMap.get(matricula);
		atual.setNome(aluno.getNome());
		return aluno.getNome() + " atualizado com sucesso";

	}

	@Path("{matricula}")
	@DELETE
	@Produces("text/plain")
	public String removeAluno(@PathParam("matricula") Integer matricula) {

		alunosMap.remove(matricula);
		return "Aluno removido.";

	}

}
