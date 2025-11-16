package com.pdg.health_back.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitRequest {
    private String nombre;
    private String descripcion;
    private Integer idTipo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime recordatorio;
    
    private List<CategoriaRequest> categorias;
}


