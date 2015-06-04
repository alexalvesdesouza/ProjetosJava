package br.pitagoras.crudRESTCliente.main;

import java.util.List;

import br.pitagoras.crudRESTCliente.cliente.AlunosClient;
import br.pitagoras.crudRESTCliente.entities.Aluno;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AlunosClient client = new AlunosClient();

		List<Aluno> alunos = client.getAlunos();
		System.out.println("");
		System.out.println("Buscar todos alunos");
		System.out.println(alunos);

		Aluno aluno = client.getAluno(1);
		System.out.println("");
		System.out.println("Buscar aluno por matricula");
		System.out.println(aluno);

		Aluno aluno2 = new Aluno();
		aluno2.setMatricula(6);
		aluno2.setNome("Ana Marta");
		String msgSucessoAdicionar = client.adicionaAluno(aluno2);
		System.out.println("");
		System.out.println("Adicionar aluno");
		System.out.println(msgSucessoAdicionar);

		Aluno aluno3 = new Aluno();
		aluno3.setMatricula(6);
		aluno3.setNome("Edialida Borges");
		String msgSucessoAtualizar = client.atualizaAluno(aluno3);
		System.out.println("");
		System.out.println("Atualizar aluno");
		System.out.println(msgSucessoAtualizar);

		Aluno aluno4 = new Aluno();
		aluno4.setMatricula(4);
		
		String msgSucessoRemover = client.removeAluno(aluno4);
		System.out.println("");
		System.out.println("Remover aluno");
		System.out.println(msgSucessoRemover);
		
		
		System.out.println("");
		System.out.println("Buscar todos alunos de novo");
		alunos = null;
		alunos = client.getAlunos();
		System.out.println(alunos);

	}

}
