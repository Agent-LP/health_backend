package com.pdg.health_back.repositories;

import com.pdg.health_back.entities.Categoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByIdUsuario(Long idUsuario);
}



