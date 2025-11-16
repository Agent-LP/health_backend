package com.pdg.health_back.services;

import com.pdg.health_back.entities.Habito;
import com.pdg.health_back.entities.HabitoTemporizado;
import com.pdg.health_back.repositories.HabitRepository;
import com.pdg.health_back.repositories.HabitoTemporizadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitoTemporizadoService {

    @Autowired
    private HabitoTemporizadoRepository habitoTemporizadoRepository;

    @Autowired
    private HabitRepository habitRepository;

    public List<HabitoTemporizado> getAllHabitosTemporizados() {
        return habitoTemporizadoRepository.findAll();
    }

    public HabitoTemporizado getHabitoTemporizadoById(Integer idHabito) {
        return habitoTemporizadoRepository.findByHabitoId(idHabito).orElse(null);
    }

    public HabitoTemporizado createHabitoTemporizado(HabitoTemporizado habitoTemporizado) {
        if (habitoTemporizado.getTiempoLogrado() == null) {
            habitoTemporizado.setTiempoLogrado(0);
        }
        return habitoTemporizadoRepository.save(habitoTemporizado);
    }

    public HabitoTemporizado updateHabitoTemporizado(Integer idHabito, HabitoTemporizado habitoTemporizado) {
        Optional<Habito> habito = habitRepository.findById(idHabito);
        if (habito.isPresent()) {
            Optional<HabitoTemporizado> existing = habitoTemporizadoRepository.findByHabitoId(idHabito);
            if (existing.isPresent()) {
                HabitoTemporizado toUpdate = existing.get();
                toUpdate.setDuracionObjetivo(habitoTemporizado.getDuracionObjetivo());
                toUpdate.setTiempoLogrado(habitoTemporizado.getTiempoLogrado());
                return habitoTemporizadoRepository.save(toUpdate);
            }
        }
        return null;
    }

    public boolean deleteHabitoTemporizado(Integer idHabito) {
        Optional<HabitoTemporizado> temporizado = habitoTemporizadoRepository.findByHabitoId(idHabito);
        if (temporizado.isPresent()) {
            habitoTemporizadoRepository.delete(temporizado.get());
            return true;
        }
        return false;
    }
}

