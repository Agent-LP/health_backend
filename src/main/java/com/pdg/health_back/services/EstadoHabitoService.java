package com.pdg.health_back.services;

import com.pdg.health_back.entities.EstadoHabito;
import com.pdg.health_back.repositories.EstadoHabitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoHabitoService {

    @Autowired
    private EstadoHabitoRepository estadoHabitoRepository;

    public List<EstadoHabito> getAllEstadosHabitos() {
        return estadoHabitoRepository.findAll();
    }

    public EstadoHabito getEstadoHabitoById(Integer id) {
        Optional<EstadoHabito> estadoHabito = estadoHabitoRepository.findById(id);
        return estadoHabito.orElse(null);
    }

    public EstadoHabito createEstadoHabito(EstadoHabito estadoHabito) {
        return estadoHabitoRepository.save(estadoHabito);
    }

    public EstadoHabito updateEstadoHabito(Integer id, EstadoHabito estadoHabito) {
        Optional<EstadoHabito> existing = estadoHabitoRepository.findById(id);
        if (existing.isPresent()) {
            estadoHabito.setIdEstado(id);
            return estadoHabitoRepository.save(estadoHabito);
        }
        return null;
    }

    public boolean deleteEstadoHabito(Integer id) {
        if (estadoHabitoRepository.existsById(id)) {
            estadoHabitoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



