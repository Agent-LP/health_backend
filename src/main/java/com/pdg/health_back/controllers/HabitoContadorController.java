package com.pdg.health_back.controllers;

import com.pdg.health_back.entities.HabitoContador;
import com.pdg.health_back.services.HabitoContadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitos-contadores")
public class HabitoContadorController {

    @Autowired
    private HabitoContadorService habitoContadorService;

    @GetMapping
    public ResponseEntity<List<HabitoContador>> getAllHabitosContadores() {
        List<HabitoContador> contadores = habitoContadorService.getAllHabitosContadores();
        return ResponseEntity.ok(contadores);
    }

    @GetMapping("/{idHabito}")
    public ResponseEntity<HabitoContador> getHabitoContadorById(@PathVariable Integer idHabito) {
        HabitoContador habitoContador = habitoContadorService.getHabitoContadorById(idHabito);
        if (habitoContador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitoContador);
    }

    @PostMapping
    public ResponseEntity<HabitoContador> createHabitoContador(@RequestBody HabitoContador habitoContador) {
        HabitoContador created = habitoContadorService.createHabitoContador(habitoContador);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{idHabito}")
    public ResponseEntity<HabitoContador> updateHabitoContador(
            @PathVariable Integer idHabito,
            @RequestBody HabitoContador habitoContador) {
        HabitoContador updated = habitoContadorService.updateHabitoContador(idHabito, habitoContador);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{idHabito}")
    public ResponseEntity<Void> deleteHabitoContador(@PathVariable Integer idHabito) {
        boolean deleted = habitoContadorService.deleteHabitoContador(idHabito);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



