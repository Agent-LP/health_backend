package com.pdg.health_back.repositories;

import com.pdg.health_back.entities.TipoHabito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoHabitoRepository extends JpaRepository<TipoHabito, Integer> {
}



