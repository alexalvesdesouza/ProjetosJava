package br.com.pgi.model.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.pgi.model.dao.ClienteDao;
import br.com.pgi.model.dao.impl.ClienteDaoImpl;
import br.com.pgi.model.entities.Cliente;
import br.com.pgi.model.util.UtilErros;
import br.com.pgi.model.util.UtilMenssagens;

@ManagedBean(name = "clienteController")
@SessionScoped
public class ClienteController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClienteDao dao;
	private Cliente cliente = new Cliente();

	public ClienteController() {
		dao = new ClienteDaoImpl();
	}

	public String listaCliente() {
		return "/privado/cliente/dataTableCliente?faces-redirect=true";
	}

	public String novo() {

		cliente = new Cliente();
		return "privado/cliente/cadastroCliente.xhtml";
	}

	public String cancelar() {

		this.cliente = new Cliente();
		UtilMenssagens.mensagemInformacao("CANCELADO!!!");
		return "cadastroCliente.xhtml";
	}

	public String gravarCliente() {
		dao.gravarCliente(cliente);
		this.cliente = new Cliente();
		return "cadastroCliente.xhtml";

	}

	public void enviarFoto(FileUploadEvent event) {
		try {
			byte[] foto = IOUtils.toByteArray(event.getFile().getInputstream());
			cliente.setFotoCliente(foto);
			UtilMenssagens.mensagemInformacao("Arquivo enviado com sucesso "
					+ event.getFile().getFileName());
		} catch (Exception e) {
			UtilMenssagens.mensagemErro("Erro ao enviar o arquivo --> "
					+ UtilErros.getMensagemErro(e));
		}
	}

	public StreamedContent getImagemDinamica() {
		String strid = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get("id_imagem");
		if (strid != null) {
			Integer id = Integer.parseInt(strid);
			Cliente obj = dao.localizar(id);
			return obj.getImagem();

		} else
			return new DefaultStreamedContent();
	}

	public String alterarCliente(Cliente obj) {
		cliente = obj;
		return "cadastroCliente.xhtml";
	}

	public String excluirCliente(Cliente obj) {
		dao.excluirCliente(obj);
		return "dataTableCliente?faces-redirect=true";
	}

	public ClienteDao getDao() {
		return dao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}