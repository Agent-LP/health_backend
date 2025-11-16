package com.pdg.health_back.services;

import com.pdg.health_back.entities.Categoria;
import com.pdg.health_back.entities.Habito;
import com.pdg.health_back.models.CategoriaRequest;
import com.pdg.health_back.models.HabitRequest;
import com.pdg.health_back.models.HabitResponse;
import com.pdg.health_back.repositories.CategoriaRepository;
import com.pdg.health_back.repositories.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<HabitResponse> getAllHabits() {
        List<Habito> habitos = habitRepository.findAll();
        return habitos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public HabitResponse getHabitById(Integer id) {
        Optional<Habito> habito = habitRepository.findById(id);
        if (habito.isPresent()) {
            return mapToResponse(habito.get());
        }
        return null;
    }

    @Transactional
    public HabitResponse createHabit(HabitRequest request, Integer idUsuario) {
        Habito habito = mapToEntity(request, idUsuario);
        
        // Set categorias if provided
        if (request.getCategorias() != null && !request.getCategorias().isEmpty()) {
            Set<Categoria> categorias = new HashSet<>();
            for (CategoriaRequest categoriaRequest : request.getCategorias()) {
                Categoria categoria = mapCategoriaRequestToEntity(categoriaRequest);
                if (categoria.getIdCategoria() != null) {
                    Optional<Categoria> existingCategoria = categoriaRepository.findById(categoria.getIdCategoria());
                    if (existingCategoria.isPresent()) {
                        categorias.add(existingCategoria.get());
                    }
                } else {
                    // If new categoria, save it first
                    Categoria savedCategoria = categoriaRepository.save(categoria);
                    categorias.add(savedCategoria);
                }
            }
            habito.setCategorias(categorias);
        }
        
        Habito savedHabito = habitRepository.save(habito);
        return mapToResponse(savedHabito);
    }

    @Transactional
    public HabitResponse updateHabit(Integer id, HabitRequest request) {
        Optional<Habito> habitoOpt = habitRepository.findById(id);
        if (habitoOpt.isPresent()) {
            Habito habito = habitoOpt.get();
            updateEntityFromRequest(habito, request);
            
            // Update categorias if provided
            if (request.getCategorias() != null) {
                Set<Categoria> categorias = new HashSet<>();
                for (CategoriaRequest categoriaRequest : request.getCategorias()) {
                    Categoria categoria = mapCategoriaRequestToEntity(categoriaRequest);
                    if (categoria.getIdCategoria() != null) {
                        Optional<Categoria> existingCategoria = categoriaRepository.findById(categoria.getIdCategoria());
                        if (existingCategoria.isPresent()) {
                            categorias.add(existingCategoria.get());
                        }
                    } else {
                        // If new categoria, save it first
                        Categoria savedCategoria = categoriaRepository.save(categoria);
                        categorias.add(savedCategoria);
                    }
                }
                habito.setCategorias(categorias);
            }
            
            Habito updatedHabito = habitRepository.save(habito);
            return mapToResponse(updatedHabito);
        }
        return null;
    }

    public boolean deleteHabit(Integer id) {
        if (habitRepository.existsById(id)) {
            habitRepository.deleteById(id);
            return true;
        }
        return false;
    }


    private HabitResponse mapToResponse(Habito habito) {
        HabitResponse response = new HabitResponse();
        response.setCategorias(habito.getCategorias());
        response.setNombre(habito.getNombre());
        response.setDescripcion(habito.getDescripcion());
        response.setFechaInicio(habito.getFechaInicio());
        response.setFechaFin(habito.getFechaFin());
        response.setRecordatorio(habito.getRecordatorio());
        return response;
    }

    private Categoria mapCategoriaRequestToEntity (CategoriaRequest request){
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setColor(request.getColor());
        return categoria;
    }

    private Habito mapToEntity(HabitRequest request, Integer idUsuario) {
        Habito habito = new Habito();
        habito.setIdUsuario(idUsuario);
        habito.setNombre(request.getNombre());
        habito.setDescripcion(request.getDescripcion());
        habito.setFechaInicio(request.getFechaInicio());
        habito.setFechaFin(request.getFechaFin());
        habito.setRecordatorio(request.getRecordatorio());
        habito.setIdTipo(request.getIdTipo());
        habito.setIdEstado(1); // Default estado "activo"
        habito.setProgreso(0.0f); // Initialize progress to 0
        return habito;
    }

    private void updateEntityFromRequest(Habito habito, HabitRequest request) {
        if (request.getNombre() != null) {
            habito.setNombre(request.getNombre());
        }
        if (request.getDescripcion() != null) {
            habito.setDescripcion(request.getDescripcion());
        }
        if (request.getFechaInicio() != null) {
            habito.setFechaInicio(request.getFechaInicio());
        }
        if (request.getFechaFin() != null) {
            habito.setFechaFin(request.getFechaFin());
        }
        if (request.getRecordatorio() != null) {
            habito.setRecordatorio(request.getRecordatorio());
        }
        if (request.getIdTipo() != null) {
            habito.setIdTipo(request.getIdTipo());
        }
    }
}
