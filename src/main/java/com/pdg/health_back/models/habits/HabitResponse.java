package com.pdg.health_back.models.habits;

import com.pdg.health_back.entities.Categoria;
import com.pdg.health_back.models.especialHabits.DuracionResponse;
import com.pdg.health_back.models.especialHabits.RepeticionesResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitResponse {
    private Set<Categoria> categorias;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime recordatorio;
    private String estado;
    private String tipo;
    private RepeticionesResponse habitoContador;
    private DuracionResponse habitoTemporizado;
}


