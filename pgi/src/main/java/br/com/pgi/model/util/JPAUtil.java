package br.com.pgi.model.util;

/**Esta Classe é necessariao quando se usa a especificação JPA. Porque?
 * Porque toda vez que se faz uma operação de um CRUD qualquer, a Interface EntityManager da um novo restart
 * no servidor de aplicação/producao. 
 * Fazendo uma classe separada de criação de Gerenciamento de entidades, esta classe cria fica instanciada 
 * de forma statica e a cada nova operação do CRUD, a EntityManager ja possui uma referencia no servidor, evitando assim
 * que fique lento a aplicação*/

/**Imports necessarios*/
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	/**
	 * Instancia que usamos para gerar uma fabrica de gerenciadores de entidade
	 * assim é mais util do que fazer um destes em cada ação do CRUD. ele passa
	 * como parametro o nome de projeto que esta definido no Persitence.xml, que
	 * é um arquivo de configuração padrao de conexao com o BD
	 */
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pgi");

	/**
	 * Gerenciador de Entidades, retorna uma EntityManager, que é a base para
	 * toda a interação com o BD
	 */
	public static EntityManager getEntityManager() {

		return emf.createEntityManager();
	}
}
