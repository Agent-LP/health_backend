package com.pdg.health_back.repositories;

import com.pdg.health_back.entities.HabitoTemporizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitoTemporizadoRepository extends JpaRepository<HabitoTemporizado, Integer> {
    @Query("SELECT ht FROM HabitoTemporizado ht WHERE ht.habito.idHabito = :id_habito")
    Optional<HabitoTemporizado> findByHabitoId(@Param("id_habito") Integer id_habito);
}

