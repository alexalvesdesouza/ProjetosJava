package br.pitagoras.crudRest.constantes;

public enum Categoria {

	CTBC("Celular"), OI("Celular"), VIVO("Celular"), CLARO("Celular"), EMBRATEL("Fixo"), GVT("Fixo"), NET(
			"Internet"), TELEFONICA("Internet"), TIM("Celular");

	Categoria(String cate) {

	}

	public String getOperadora() {
		return Categoria.values().toString();
	}

}
