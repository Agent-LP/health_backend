package com.pdg.health_back.controllers;

import com.pdg.health_back.entities.TipoHabito;
import com.pdg.health_back.services.TipoHabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-habitos")
public class TipoHabitoController {

    @Autowired
    private TipoHabitoService tipoHabitoService;

    @GetMapping
    public ResponseEntity<List<TipoHabito>> getAllTiposHabitos() {
        List<TipoHabito> tipos = tipoHabitoService.getAllTiposHabitos();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoHabito> getTipoHabitoById(@PathVariable Integer id) {
        TipoHabito tipoHabito = tipoHabitoService.getTipoHabitoById(id);
        if (tipoHabito == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tipoHabito);
    }

    @PostMapping
    public ResponseEntity<TipoHabito> createTipoHabito(@RequestBody TipoHabito tipoHabito) {
        TipoHabito created = tipoHabitoService.createTipoHabito(tipoHabito);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoHabito> updateTipoHabito(
            @PathVariable Integer id,
            @RequestBody TipoHabito tipoHabito) {
        TipoHabito updated = tipoHabitoService.updateTipoHabito(id, tipoHabito);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoHabito(@PathVariable Integer id) {
        boolean deleted = tipoHabitoService.deleteTipoHabito(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



