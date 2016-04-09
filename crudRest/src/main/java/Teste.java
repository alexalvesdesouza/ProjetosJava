import java.util.Date;

import br.pitagoras.crudRest.constantes.Categoria;
import br.pitagoras.crudRest.dao.ContatosDAO;
import br.pitagoras.crudRest.dao.OperadorasDAO;
import br.pitagoras.crudRest.dao.impl.ContatoImpl;
import br.pitagoras.crudRest.dao.impl.OperadoraImpl;
import br.pitagoras.crudRest.entities.ContatoDTO;
import br.pitagoras.crudRest.entities.OperadoraDTO;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OperadoraDTO oi = new OperadoraDTO();
		oi.setCodOperadora(3);
		ContatoDTO c = new ContatoDTO();
		c.setNome("Caroline Dantas Herningg");
		c.setData(new Date());
		c.setDesCpf("240.876.543-99");
		c.setDesEmail("carol.1977y@gmail.com");
		c.setDesSexo("F");
		c.setTelefone("8887-8980");
		c.setOperadora(oi);
		
		
		
		ContatosDAO impl = new ContatoImpl();
		OperadorasDAO operadoraDAO = new OperadoraImpl();
		impl.cadastraContato(c);
		System.out.println(Categoria.CLARO.getOperadora().toString());
		System.out.println(operadoraDAO.listaDeOperadoras());
		System.out.println(impl.listaContatos());
	}

}
