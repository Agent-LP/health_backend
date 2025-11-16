package com.pdg.health_back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "habitos_temporizados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitoTemporizado {

    @Id
    @Column(name = "id_habito")
    private Integer idHabito;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_habito", nullable = false)
    private Habito habito;

    @Column(name = "duracion_objetivo", nullable = false)
    private Integer duracionObjetivo; // en minutos

    @Column(name = "tiempo_logrado", nullable = false)
    private Integer tiempoLogrado; // en minutos, default 0
}



