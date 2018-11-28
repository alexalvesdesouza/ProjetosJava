package com.ideaapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ideaapi.model.Agenda;
import com.ideaapi.repository.AgendaRepository;
import com.ideaapi.repository.filter.AgendaFilter;
import com.ideaapi.repository.projection.ResumoAgendamento;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendamentoRepository;

    @Autowired
    private HorarioService horarioService;

    public Page<Agenda> listaTodasAgendamentos(AgendaFilter filter, Pageable pageable) {
        return this.agendamentoRepository.filtrar(filter, pageable);
    }

    public Page<ResumoAgendamento> resumo(AgendaFilter filter, Pageable pageable) {
        return this.agendamentoRepository.resumir(filter, pageable);
    }

    public Agenda cadastraAgenda(Agenda entity) {

        if(!entity.getHorarios().isEmpty()) {
            entity.getHorarios().forEach(this.horarioService::cadastraHorario);
        }
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
