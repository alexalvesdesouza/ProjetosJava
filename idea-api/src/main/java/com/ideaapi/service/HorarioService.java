package com.ideaapi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ideaapi.model.Agenda;
import com.ideaapi.model.Horario;
import com.ideaapi.repository.AgendaRepository;
import com.ideaapi.repository.HorarioRepository;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    public List<Horario> listaHorariosDos3ProximosDias() {

        List<Horario> horarios = new ArrayList<>();
        List<Agenda> agendasDos3ProximosDias = agendaRepository.findAllByDiaAgendaBefore(LocalDate.now().plusDays(3L));

        if (!agendasDos3ProximosDias.isEmpty())
            agendasDos3ProximosDias.forEach(agenda ->
                    agenda.getHorarios().forEach(horario ->
                            horarios.add(getHorarioWithHoraExameCompleta(agenda, horario)
                            )
                    )
            );

        return horarios;
    }

    public Horario cadastraHorario(Horario entity) {
        entity.setAvulso(false);
        entity.setRestante(entity.getMaximoPermitido());
        entity.setDisponivel(true);
        return this.horarioRepository.save(entity);
    }

    public Horario buscaHorario(Long codigo) {
        Horario horario = this.horarioRepository.findOne(codigo);
        if (horario != null) {
            return horario;
        }

        return null;
    }

    public void deletaHorario(Long codigo) {
        this.horarioRepository.delete(codigo);
    }

    public void queimaHorario(Horario horario) {
        int restante = horario.getRestante();
        horario.setRestante(restante - 1);
        this.horarioRepository.save(horario);
    }

    private Horario getHorarioWithHoraExameCompleta(Agenda agenda, Horario horario) {

        horario.setHoraExame(horario.getHoraExame() + " - " + agenda.getDiaAgenda().getDayOfMonth() + " "
                + agenda.getDiaAgenda().getMonth().toString() + " " + agenda.getDiaAgenda().getYear());

        return horario;
    }
}
