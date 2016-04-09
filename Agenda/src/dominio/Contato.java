package dominio;

public class Contato {
	
	private String nome;
	private String telefone;
	private String email;
	private Endereco endereco;
	
	
	//usando construtor de copia
	public Contato (Contato c){
		
		this.nome = c.nome;
		this.telefone = c.telefone;
		this.email = c.email;
		this.endereco = c.endereco;
		
	}
	
	
	//construtor especial com a passagem de todos os parametros
	public Contato(String nome, String telefone, String email, Endereco endereco) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nome: " + this.getNome() +  "\n" +
		"Telefone: " + this.getTelefone() +  "\n" +
		"E-mail: " + this.getEmail() +  "\n" +
		this.getEndereco() + "\n" ;
	}
	

}
