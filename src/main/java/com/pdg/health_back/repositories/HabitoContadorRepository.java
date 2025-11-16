package com.pdg.health_back.repositories;

import com.pdg.health_back.entities.HabitoContador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitoContadorRepository extends JpaRepository<HabitoContador, Integer> {
    @Query("SELECT hc FROM HabitoContador hc WHERE hc.habito.idHabito = :id_habito")
    Optional<HabitoContador> findByHabitoId(@Param("id_habito") Integer id_habito);
}

