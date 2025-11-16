package com.pdg.health_back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "habitos_contadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitoContador {

    @Id
    @Column(name = "id_habito")
    private Integer idHabito;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_habito", nullable = false)
    private Habito habito;

    @Column(name = "repeticiones_objetivo", nullable = false)
    private Integer repeticionesObjetivo;

    @Column(name = "repeticiones_logradas", nullable = false)
    private Integer repeticionesLogradas; // default 0
}



