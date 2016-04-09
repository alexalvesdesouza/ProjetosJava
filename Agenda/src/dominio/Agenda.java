package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Agenda {

	List<Contato> contatos = new ArrayList<>();

	// metodo para adicionar contato com construtor de copia
	public void adicionarContato(Contato novo) {
		Contato n = new Contato(novo);

		contatos.add(n);
	}

	// metodo para remover um contato pelo nome
	public boolean removerContato(Contato remove) {

		contatos.remove(remove);

		return true;
	}

	// metodo que ira pesquisar um contato pelo nome
	public Contato pesquisarContato(String nome) {
		Contato temp = null;
		for (Contato pesquisar : contatos) {
			temp = pesquisar;
			if (temp.getNome().equalsIgnoreCase(nome))

				return temp;
		}

		return null;
	}

	// metodo que ira pesquisar um contato pelo nome e telefone
	public Contato pesquisarContato(String nome, String fone) {
		Contato temp = null;
		for (Contato listar : contatos) {
			temp = listar;
			if (temp.getNome().equalsIgnoreCase(nome)
					&& temp.getTelefone().equalsIgnoreCase(fone)) {
				return temp;

			}

		}

		return null;

	}

	// metodo que ira alterar um contato
	public boolean alterarContato(Contato antigo, Contato novo) {

		removerContato(antigo);
		adicionarContato(novo);

		return true;
	}

	// metodo que ire informar a quantidade de contatos na agenda
	public int getQuantidade() {
		int total = contatos.size();
		return total;

	}

	// metodo que ira imprimir os contatos
	public void imprimiAgenda() {
		for (Contato listar : contatos) {
			System.out.println(listar);

		}
	}

	// metodo que ira listar todos os contatos da lista
	public List<Contato> getListaContatos() {
		return contatos;
	}

	public int contatoHash(Contato cont) {

		int rec = cont.hashCode();

		return rec;
	}

	public int pesquisaIndex(Contato pega) {
		int index = contatos.indexOf(pega);
		return index;
	}

	public Contato pespuisaNomePorIndice(int index) {
		Contato retorna = contatos.get(index);
		return retorna;
	}

	public void imprimiOrdemAlfabetica() {

		Collections.reverse(contatos);
		for (Contato cont : contatos) {
			System.out.println(contatos);

		}
	}
	
	public boolean comparaHash(int um, int dois){
		
		return true;
	}

}
