package com.pdg.health_back.controllers;

import com.pdg.health_back.entities.HabitoTemporizado;
import com.pdg.health_back.models.DuracionRequest;
import com.pdg.health_back.services.HabitoTemporizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitos-temporizados")
public class HabitoTemporizadoController {

    @Autowired
    private HabitoTemporizadoService habitoTemporizadoService;

    @GetMapping
    public ResponseEntity<List<HabitoTemporizado>> getAllHabitosTemporizados() {
        List<HabitoTemporizado> temporizados = habitoTemporizadoService.getAllHabitosTemporizados();
        return ResponseEntity.ok(temporizados);
    }

    @GetMapping("/{idHabito}")
    public ResponseEntity<HabitoTemporizado> getHabitoTemporizadoById(@PathVariable Integer idHabito) {
        HabitoTemporizado habitoTemporizado = habitoTemporizadoService.getHabitoTemporizadoById(idHabito);
        if (habitoTemporizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitoTemporizado);
    }

    @PostMapping
    public ResponseEntity<HabitoTemporizado> createHabitoTemporizado(@RequestBody HabitoTemporizado habitoTemporizado) {
        HabitoTemporizado created = habitoTemporizadoService.createHabitoTemporizado(habitoTemporizado);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{idHabito}")
    public ResponseEntity<HabitoTemporizado> updateHabitoTemporizado(
            @PathVariable Integer idHabito,
            @RequestBody DuracionRequest habitoTemporizado) {
        HabitoTemporizado updated = habitoTemporizadoService.updateHabitoTemporizado(idHabito, habitoTemporizado);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{idHabito}")
    public ResponseEntity<Void> deleteHabitoTemporizado(@PathVariable Integer idHabito) {
        boolean deleted = habitoTemporizadoService.deleteHabitoTemporizado(idHabito);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}



