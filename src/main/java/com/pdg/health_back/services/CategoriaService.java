package com.pdg.health_back.services;

import com.pdg.health_back.entities.Categoria;
import com.pdg.health_back.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> getAllCategoriasByUserId(Long IdUsuario) {
        return categoriaRepository.findByIdUsuario(IdUsuario);
    }

    public Categoria getCategoriaById(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElse(null);
    }

    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria updateCategoria(Integer id, Categoria categoria) {
        Optional<Categoria> existing = categoriaRepository.findById(id);
        if (existing.isPresent()) {
            categoria.setIdCategoria(id);
            return categoriaRepository.save(categoria);
        }
        return null;
    }

    public boolean deleteCategoria(Integer id) {
        if (categoriaRepository.existsById(id)) {
            categoriaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



