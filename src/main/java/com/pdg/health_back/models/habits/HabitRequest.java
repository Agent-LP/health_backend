package com.pdg.health_back.models.habits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.pdg.health_back.models.CategoriaRequest;
import com.pdg.health_back.models.especialHabits.DuracionRequest;
import com.pdg.health_back.models.especialHabits.RepeticionesRequest;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitRequest {
    private String nombre;
    private String descripcion;
    private Integer idTipo;
    private Integer idEstado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime recordatorio;
    
    private List<CategoriaRequest> categorias;
    private RepeticionesRequest repeticiones;
    private DuracionRequest duracion;
}


