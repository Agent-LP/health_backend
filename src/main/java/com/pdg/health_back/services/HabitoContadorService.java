package com.pdg.health_back.services;

import com.pdg.health_back.entities.Habito;
import com.pdg.health_back.entities.HabitoContador;
import com.pdg.health_back.models.especialHabits.RepeticionesRequest;
import com.pdg.health_back.repositories.HabitRepository;
import com.pdg.health_back.repositories.HabitoContadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitoContadorService {

    @Autowired
    private HabitoContadorRepository habitoContadorRepository;

    @Autowired
    private HabitRepository habitRepository;

    public List<HabitoContador> getAllHabitosContadores() {
        return habitoContadorRepository.findAll();
    }

    public HabitoContador getHabitoContadorById(Integer idHabito) {
        return habitoContadorRepository.findByHabitoId(idHabito).orElse(null);
    }

    public HabitoContador createHabitoContador(HabitoContador habitoContador) {
        if (habitoContador.getRepeticionesLogradas() == null) {
            habitoContador.setRepeticionesLogradas(0);
        }
        return habitoContadorRepository.save(habitoContador);
    }

    public HabitoContador updateHabitoContador(Integer idHabito, RepeticionesRequest habitoContador) {
        Optional<Habito> habito = habitRepository.findById(idHabito);
        if (habito.isPresent()) {
            Optional<HabitoContador> existing = habitoContadorRepository.findByHabitoId(idHabito);
            if (existing.isPresent()) {
                HabitoContador toUpdate = existing.get();
                toUpdate.setRepeticionesObjetivo(habitoContador.getRepeticionesObjetivo());
                toUpdate.setRepeticionesLogradas(habitoContador.getRepeticionesLogradas());
                return habitoContadorRepository.save(toUpdate);
            }
        }
        return null;
    }

    public boolean deleteHabitoContador(Integer idHabito) {
        Optional<HabitoContador> contador = habitoContadorRepository.findByHabitoId(idHabito);
        if (contador.isPresent()) {
            habitoContadorRepository.delete(contador.get());
            return true;
        }
        return false;
    }
}

