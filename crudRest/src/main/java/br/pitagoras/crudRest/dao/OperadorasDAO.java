package br.pitagoras.crudRest.dao;

import java.util.List;

import br.pitagoras.crudRest.entities.OperadoraDTO;

public interface OperadorasDAO {

	public OperadoraDTO cadastraOperadora(OperadoraDTO operadora);

	public List<OperadoraDTO> listaDeOperadoras();

	public OperadoraDTO deletaOperadora(OperadoraDTO operadora);

	public OperadoraDTO alteraOperadora(OperadoraDTO operadora);

	public OperadoraDTO getOperadora(Integer codigo);

}
