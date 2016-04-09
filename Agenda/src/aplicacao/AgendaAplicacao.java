package aplicacao;

import java.util.Scanner;

import dominio.Agenda;
import dominio.Contato;
import dominio.Endereco;

public class AgendaAplicacao {

	
	//testse
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);

		Agenda age = new Agenda();
		Agenda ageNovo;

		Endereco end = new Endereco("", "", "", "", "", "");
		Endereco endNovo;

		int opcao = 0;

		do {
			System.out
					.println("Entre com uma das opçoes abaixo ou 13 para sair");
			System.out.println("1 - Incluir Contato\n"
					+ "2 - Remover Contato\n" 
					+ "3 - Alterar Contato\n"
					+ "4 - Ver o numero hashdo contato\n"
					+ "5 - Pesquisar Contato Pelo Nome\n"
					+ "6 - Pesqusiar Contato pelo Telefone\n"
					+ "7 - Listar Contatos da Agenda\n"
					+ "8 - Ver Quantidade de Contatos Da Agenda\n"
					+ "9 - Digitar um indice e ver qual o nome do contado\n"
					+ "10 - Comparar dois HasHcodes\n"
					+ "11 - Ver a posição do contato na Agenda\n"
					+ "12 - Listar Contatos em ordem reversa\n"
					+ "13- Sair do sistema");

			opcao = s.nextInt();

			switch (opcao) {

			case 1:
				System.out.print("Nome: ");
				String nome = s.next();
				System.out.print("Telefone: ");
				String telefone = s.next();
				System.out.print("E-mail: ");
				String email = s.next();
				System.out.print("Rua: ");
				String rua = s.next();
				System.out.print("Numero: ");
				String numero = s.next();
				System.out.print("Bairro: ");
				String bairro = s.next();
				System.out.print("Cep: ");
				String cep = s.next();
				System.out.print("Cidade: ");
				String cidade = s.next();
				System.out.print("Estado: ");
				String estado = s.next();

				end = new Endereco(rua, numero, bairro, cep, cidade, estado);

				Contato cont = new Contato(nome, telefone, email, end);

				// fazendo a passagem do para o construtor de copia
				Contato contatos = new Contato(cont);

				age.adicionarContato(contatos);

				break;

			case 2:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.println("Entre com o nome da pessoa que deseja retirar da agenda");
					String remove = s.next();

					Contato rem = age.pesquisarContato(remove);

					if (rem != null) {

						age.removerContato(rem);
						System.out.println("Contato: " + remove
								+ " Removido com Sucesso!!\n");

					} else {
						System.out.println("Nome não consta na agenda: ");
					}
				}

				break;

			case 3:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.println("Entre com o nome do contato que deseja alterar");
					String user = s.next();
					Contato altera = age.pesquisarContato(user);
					if (altera != null) {

						System.out.print("Nome: ");
						String nomeA = s.next();
						System.out.print("Telefone: ");
						String telefoneA = s.next();
						System.out.print("E-mail: ");
						String emailA = s.next();
						System.out.print("Rua: ");
						String ruaA = s.next();
						System.out.print("Numero: ");
						String numeroA = s.next();
						System.out.print("Bairro: ");
						String bairroA = s.next();
						System.out.print("Cep: ");
						String cepA = s.next();
						System.out.print("Cidade: ");
						String cidadeA = s.next();
						System.out.print("Estado: ");
						String estadoA = s.next();

						end = new Endereco(ruaA, numeroA, bairroA, cepA,
								cidadeA, estadoA);

						Contato contAlerado = new Contato(nomeA, telefoneA,
								emailA, end);

						// fazendo a passagem do para o construtor de copia
						Contato contatosAlterado = new Contato(contAlerado);

						age.alterarContato(altera, contatosAlterado);
						System.out.println("Contato Alterado Com Sucesso!!!\n");

					} else {
						System.out
								.println("Não foi possivel alterar, Contato inexistente!!");
					}
				}

				break;

			case 4:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.println("Entre com o nome do contato que deseja ver o seu hascode");
					String contHash = s.next();
					Contato pHash = age.pesquisarContato(contHash);
					int codHash = age.contatoHash(pHash);
					System.out.println("O codigo hash do contato: " + contHash
							+ " é: " + codHash);
				}
				break;

			case 5:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.println("Digige o nome da pessoa que deseja pesquisar: ");
					String pesquisa = s.next();
					Contato temp = age.pesquisarContato(pesquisa);
					if (temp != null) {
						System.out.println(temp);
					} else {
						System.out.println("Contato inexistente:");
						System.out.println();
					}
				}
				break;

			case 6:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.println("Entre com o telefone e o nome da pessoa que deseja pesquisar: ");
					System.out.print("Fone: ");
					String fonePesquisa = s.next();
					System.out.print("Nome: ");
					String nomePesquisa = s.next();

					Contato pesq = age.pesquisarContato(nomePesquisa,
							fonePesquisa);
					if (pesq != null) {
						System.out.println(pesq);
					} else {
						System.out
								.println("Contato ou Telefone estão errados.!! ");
					}
				}

				break;

			case 7:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out.println("Lista de Contatos ");
					// impressao direto pelo arraylist
					// System.out.println(age.getListaContatos());
					age.imprimiAgenda();
					System.out.println();
				}
				break;

			case 8:
				System.out.println("A Agenda Possui " + age.getQuantidade()
						+ " Contatos.");

				break;

			case 9:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.print("Entre com um indice para ver o nome do contato: ");
					int index = s.nextInt();
					Contato indexPessoa = age.pespuisaNomePorIndice(index);
					if (indexPessoa != null) {
						System.out.println(indexPessoa);
					} else {
						System.out.println("Nao Existe o indice: " + index
								+ " na agenda!!");
					}

				}

				break;
			case 10:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out.println("Entre com o 1° nome");
					String nome1 = s.next();
					Contato nomes = age.pesquisarContato(nome1);

					System.out.println("Entre com o 2° nome");
					String nome2 = s.next();
					Contato nomes2 = age.pesquisarContato(nome2);

					if (nome1 != null && nome2 != null) {
						int confere1 = age.contatoHash(nomes);
						int confere2 = age.contatoHash(nomes2);
						if (confere1 == confere2) {
							System.out.println("REGISTRO IGUAL");
						} else {
							System.out.println("REGISTROS DIFERENTES");
						}

					}
				}

				break;
			case 11:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out
							.println("Entre com o nome do contato para saber qual a sua posição na agenda de contatos");
					String pegaIndex = s.next();
					Contato pega = age.pesquisarContato(pegaIndex);
					int numIndex = age.pesquisaIndex(pega);
					System.out.println(pegaIndex + " Indice: " + numIndex);
				}
				break;
			case 12:
				if (age.getQuantidade() == 0) {
					System.out.println("Agenda não possui contatos!!");
				} else {
					System.out.println("Lista em ordem reversa");
					age.imprimiOrdemAlfabetica();
				}
				break;
			case 13:
				System.out
						.println("**********SISTEMA ENCERRADO COM SUCESSO**********");

				break;

			default:
				System.out.println("Opcao Invalida, tente novamente");
				break;

			}

		} while (opcao != 13);

	}

}
