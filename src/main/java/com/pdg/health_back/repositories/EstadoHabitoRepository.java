package com.pdg.health_back.repositories;

import com.pdg.health_back.entities.EstadoHabito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoHabitoRepository extends JpaRepository<EstadoHabito, Integer> {
}



