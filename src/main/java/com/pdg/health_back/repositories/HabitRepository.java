package com.pdg.health_back.repositories;

import com.pdg.health_back.entities.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habito, Integer> {
}
