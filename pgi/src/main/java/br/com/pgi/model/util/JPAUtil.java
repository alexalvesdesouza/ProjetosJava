package br.com.pgi.model.util;

/**Esta Classe � necessariao quando se usa a especifica��o JPA. Porque?
 * Porque toda vez que se faz uma opera��o de um CRUD qualquer, a Interface EntityManager da um novo restart
 * no servidor de aplica��o/producao. 
 * Fazendo uma classe separada de cria��o de Gerenciamento de entidades, esta classe cria fica instanciada 
 * de forma statica e a cada nova opera��o do CRUD, a EntityManager ja possui uma referencia no servidor, evitando assim
 * que fique lento a aplica��o*/

/**Imports necessarios*/
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	/**
	 * Instancia que usamos para gerar uma fabrica de gerenciadores de entidade
	 * assim � mais util do que fazer um destes em cada a��o do CRUD. ele passa
	 * como parametro o nome de projeto que esta definido no Persitence.xml, que
	 * � um arquivo de configura��o padrao de conexao com o BD
	 */
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pgi");

	/**
	 * Gerenciador de Entidades, retorna uma EntityManager, que � a base para
	 * toda a intera��o com o BD
	 */
	public static EntityManager getEntityManager() {

		return emf.createEntityManager();
	}
}
