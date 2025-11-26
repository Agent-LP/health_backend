package com.pdg.health_back.models.especialHabits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepeticionesResponse {
    private Integer idHabito;
    private Integer repeticionesObjetivo;
    private Integer repeticionesLogradas;
}
