package com.ideaapi.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "horario")
@SequenceGenerator(name = "horario_seq", sequenceName = "horario_seq", allocationSize = 1)
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "horario_seq")
    private Long codigo;

    private LocalDate dataExame;

    private String horaExame;

    private Boolean disponivel;
}
