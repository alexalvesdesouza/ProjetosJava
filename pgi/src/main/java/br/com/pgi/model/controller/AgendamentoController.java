package br.com.pgi.model.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.pgi.model.dao.AgendamentoDao;
import br.com.pgi.model.dao.ClienteDao;
import br.com.pgi.model.dao.impl.AgendamentoDaoImpl;
import br.com.pgi.model.dao.impl.ClienteDaoImpl;
import br.com.pgi.model.dao.impl.OperadorDoSistemaDaoImpl;
import br.com.pgi.model.dao.impl.PromocaoDaoImpl;
import br.com.pgi.model.entities.Agendamento;
import br.com.pgi.model.util.ConverterCliente;
import br.com.pgi.model.util.ConverterOperador;
import br.com.pgi.model.util.ConverterPromocao;
import br.com.pgi.model.util.UtilMenssagens;

@ManagedBean(name = "agendamentoController")
@RequestScoped
public class AgendamentoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AgendamentoDao dao;// = new AgendamentoDaoImpl();
	private Agendamento agendamento = new Agendamento();
	private ClienteDao cliDao;
	private OperadorDoSistemaDaoImpl opDao;
	private ConverterCliente converterCliente;
	private ConverterOperador converterOperador;
	private PromocaoDaoImpl promoDao;
	private ConverterPromocao converterPromocao;

	public AgendamentoController() {
		dao = new AgendamentoDaoImpl();
		cliDao = new ClienteDaoImpl();
		opDao = new OperadorDoSistemaDaoImpl();
		converterCliente = new ConverterCliente();
		converterOperador = new ConverterOperador();
		converterPromocao = new ConverterPromocao();
		promoDao = new PromocaoDaoImpl();
	}

	public String listaAgenda() {
		return "privado/agenda/minhaAgenda?faces-redirect=true";
	}

	public String listaAgendaOperador() {

		return "listaTeste?faces-redirect=true";
	}

	public String ajuda() {
		return "/ajuda.xhtml";
	}

	public String novo() {
		agendamento = new Agendamento();
		return "privado/agenda/agendar.xhtml";
	}

	public String cancelarOperacao() {

		return "minhaAgenda.xhtml";
	}

	public String gravarAgendamento() {

		if (!this.agendamento.getOperadordosistema().getStatusOperador()) {

			UtilMenssagens
					.mensagemErro("Agendamento Não Efetuado - Operador esta Bloqueado. Contact o Administrador do Sistema");
			return "agendar.xhtml";
		} else {

			if (this.agendamento.getIdAgendamentos() != 0) {
				System.out.println("passou dentro do if 0 --> "
						+ this.agendamento.getIdAgendamentos());
				dao.gravarAgendamento(this.agendamento);
				this.agendamento = new Agendamento();

			} else {

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				Date today = new Date();
				dateFormat.format(today);

				if (agendamento.getDataAgendamento().getTime() < today
						.getTime()) {

					UtilMenssagens
							.mensagemErro("Agendamento Não Efetuado - Data selecionada é menor que a data de atual");
					return "agendar.xhtml";

				} else {

					Date horaInicioAgendamento = agendamento
							.getHorarioInicioAgendamento();
					Date horaFimAgendamento = agendamento
							.getHorarioFimAgendamento();

					GregorianCalendar horaInit = new GregorianCalendar();
					GregorianCalendar horaFim = new GregorianCalendar();

					horaInit.setTime(horaInicioAgendamento);
					horaFim.setTime(horaFimAgendamento);

					horaInit.add(horaInit.HOUR, 3);
					horaFim.add(horaFim.HOUR, 3);

					horaInit.add(horaInit.YEAR, 44);
					horaFim.add(horaInit.YEAR, 44);

					horaInicioAgendamento = horaInit.getTime();
					horaFimAgendamento = horaFim.getTime();

					agendamento
							.setHorarioInicioAgendamento(horaInicioAgendamento);
					agendamento.setHorarioFimAgendamento(horaFimAgendamento);

					if (agendamento.getHorarioInicioAgendamento().getTime() > agendamento
							.getHorarioFimAgendamento().getTime()) {

						UtilMenssagens
								.mensagemErro("Agendamento Não Efetuado - hora inicial é maior do que a hora final");
						return "agendar.xhtml";

					} else {

						dao.gravarAgendamento(agendamento);
						this.agendamento = new Agendamento();

						UtilMenssagens
								.mensagemInformacao("Agendamento Efetuado com sucesso");

					}
				}

			}

		}

		return "agendar.xhtml";

	}

	public String alterarAgendamento(Agendamento obj) {
		agendamento = obj;
		return "agendar.xhtml";
	}

	public String cancelarAgendamento(Agendamento obj) {
		dao.excluirAgendamento(obj);
		return "minhaAgenda?faces-redirect=true";
	}

	public AgendamentoDao getDao() {
		return dao;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public ClienteDao getCliDao() {
		return cliDao;
	}

	public void setCliDao(ClienteDaoImpl cliDao) {
		this.cliDao = cliDao;
	}

	public OperadorDoSistemaDaoImpl getOpDao() {
		return opDao;
	}

	public void setOpDao(OperadorDoSistemaDaoImpl opDao) {
		this.opDao = opDao;
	}

	public ConverterCliente getConverterCliente() {
		return converterCliente;
	}

	public void setConverterCliente(ConverterCliente converterCliente) {
		this.converterCliente = converterCliente;
	}

	public ConverterOperador getConverterOperador() {
		return converterOperador;
	}

	public void setConverterOperador(ConverterOperador converterOperador) {
		this.converterOperador = converterOperador;
	}

	public PromocaoDaoImpl getPromoDao() {
		return promoDao;
	}

	public void setPromoDao(PromocaoDaoImpl promoDao) {
		this.promoDao = promoDao;
	}

	public ConverterPromocao getConverterPromocao() {
		return converterPromocao;
	}

	public void setConverterPromocao(ConverterPromocao converterPromocao) {
		this.converterPromocao = converterPromocao;
	}
}
