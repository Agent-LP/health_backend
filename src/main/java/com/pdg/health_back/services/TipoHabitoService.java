package com.pdg.health_back.services;

import com.pdg.health_back.entities.TipoHabito;
import com.pdg.health_back.repositories.TipoHabitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoHabitoService {

    @Autowired
    private TipoHabitoRepository tipoHabitoRepository;

    public List<TipoHabito> getAllTiposHabitos() {
        return tipoHabitoRepository.findAll();
    }

    public TipoHabito getTipoHabitoById(Integer id) {
        Optional<TipoHabito> tipoHabito = tipoHabitoRepository.findById(id);
        return tipoHabito.orElse(null);
    }

    public TipoHabito createTipoHabito(TipoHabito tipoHabito) {
        return tipoHabitoRepository.save(tipoHabito);
    }

    public TipoHabito updateTipoHabito(Integer id, TipoHabito tipoHabito) {
        Optional<TipoHabito> existing = tipoHabitoRepository.findById(id);
        if (existing.isPresent()) {
            tipoHabito.setIdTipo(id);
            return tipoHabitoRepository.save(tipoHabito);
        }
        return null;
    }

    public boolean deleteTipoHabito(Integer id) {
        if (tipoHabitoRepository.existsById(id)) {
            tipoHabitoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}



