package com.pdg.health_back.controllers;

import com.pdg.health_back.entities.EstadoHabito;
import com.pdg.health_back.services.EstadoHabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-habitos")
public class EstadoHabitoController {

    @Autowired
    private EstadoHabitoService estadoHabitoService;

    @GetMapping
    public ResponseEntity<List<EstadoHabito>> getAllEstadosHabitos() {
        List<EstadoHabito> estados = estadoHabitoService.getAllEstadosHabitos();
        return ResponseEntity.ok(estados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoHabito> getEstadoHabitoById(@PathVariable Integer id) {
        EstadoHabito estadoHabito = estadoHabitoService.getEstadoHabitoById(id);
        if (estadoHabito == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estadoHabito);
    }

    @PostMapping
    public ResponseEntity<EstadoHabito> createEstadoHabito(@RequestBody EstadoHabito estadoHabito) {
        EstadoHabito created = estadoHabitoService.createEstadoHabito(estadoHabito);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoHabito> updateEstadoHabito(
            @PathVariable Integer id,
            @RequestBody EstadoHabito estadoHabito) {
        EstadoHabito updated = estadoHabitoService.updateEstadoHabito(id, estadoHabito);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstadoHabito(@PathVariable Integer id) {
        boolean deleted = estadoHabitoService.deleteEstadoHabito(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



