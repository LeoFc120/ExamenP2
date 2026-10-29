package com.example.examen.controllers;

import com.example.examen.models.Tarea;
import com.example.examen.services.TareaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {
    private final TareaService tareaService;

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Tarea tarea, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tareaService.crear(authentication.getName(), tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizar(@PathVariable Long id, @RequestBody Tarea datos,
                                            Authentication authentication) {
        return ResponseEntity.ok(tareaService.actualizarEstado(authentication.getName(), id, datos.getEstado()));
    }
}