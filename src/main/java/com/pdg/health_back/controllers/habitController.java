package com.pdg.health_back.controllers;

import com.pdg.health_back.models.habits.HabitRequest;
import com.pdg.health_back.models.habits.HabitResponse;
import com.pdg.health_back.services.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class habitController {
    
    @Autowired
    private HabitService habitService;

    @GetMapping
    public ResponseEntity<List<HabitResponse>> getAllHabits() {
        List<HabitResponse> habits = habitService.getAllHabits();
        return ResponseEntity.ok(habits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitResponse> getHabitById(@PathVariable Integer id) {
        HabitResponse habitResponse = habitService.getHabitById(id);
        if (habitResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitResponse);
    }

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(
            @RequestBody HabitRequest request,
            @RequestParam(required = false) Integer idUsuario) {
        if (idUsuario == null) {
            return ResponseEntity.badRequest().build();
        }
        HabitResponse habitResponse = habitService.createHabit(request, idUsuario);
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(habitResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitResponse> updateHabit(
            @PathVariable Integer id,
            @RequestBody HabitRequest request,
            @RequestParam(required = false) Integer idUsuario) {
        HabitResponse habitResponse = habitService.updateHabit(id, request, idUsuario);
        if (habitResponse == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(habitResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable Integer id) {
        boolean deleted = habitService.deleteHabit(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
