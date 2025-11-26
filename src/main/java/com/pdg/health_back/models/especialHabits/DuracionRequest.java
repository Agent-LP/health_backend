package com.pdg.health_back.models.especialHabits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DuracionRequest {
    private Integer duracionObjetivo; // en minutos
    private Integer tiempoLogrado;
}

