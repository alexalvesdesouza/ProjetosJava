package com.ideaapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ideaapi.model.Agenda;
import com.ideaapi.model.Funcionario;
import com.ideaapi.repository.AgendaRepository;
import com.ideaapi.repository.filter.AgendamentoFilter;
import com.ideaapi.repository.projection.ResumoAgendamento;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendamentoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    public Page<Agenda> listaTodasAgendamentos(AgendamentoFilter filter, Pageable pageable) {
        return this.agendamentoRepository.filtrar(filter, pageable);
    }

    public Page<ResumoAgendamento> resumo(AgendamentoFilter filter, Pageable pageable) {
        return this.agendamentoRepository.resumir(filter, pageable);
    }

    public Agenda cadastraAgendamento(Agenda entity) {

        Funcionario funcionario = entity.getFuncionario();
        funcionario =  this.funcionarioService.buscaFuncionario(funcionario.getCodigo());

//        if (funcionario != null && funcionario.getEmpresa() != null) {
//
//            final Empresa empresa = funcionario.getEmpresa();
//
//            if (empresa == null || empresa.isInativa()) {
//                throw new EmpesaInexsistenteOuInativaException();
//            }
//
//        }
        return this.agendamentoRepository.save(entity);
    }

    public Agenda buscaAgendamento(Long codigo) {
        Agenda agenda = this.agendamentoRepository.findOne(codigo);

        if (agenda == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return agenda;
    }

    public void deletaAgendamento(Long codigo) {
        this.agendamentoRepository.delete(codigo);
    }

    public ResponseEntity<Agenda> atualizaAgendamento(Long codigo, Agenda agenda) {
        Agenda agendaSalva = this.buscaAgendamento(codigo);
        BeanUtils.copyProperties(agenda, agendaSalva, "codigo");

        this.agendamentoRepository.save(agendaSalva);
        return ResponseEntity.ok(agendaSalva);
    }

}
