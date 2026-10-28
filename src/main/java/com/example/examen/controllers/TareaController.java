package com.example.examen.controllers;

import com.example.examen.models.Proyecto;
import com.example.examen.models.Tarea;
import com.example.examen.repositories.ProyectoRepository;
import com.example.examen.repositories.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {
    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    @PostMapping
    public ResponseEntity<?> crearTarea(@RequestBody Tarea tarea) {
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe incluir el id del proyecto en el JSON");
        }

        Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        tarea.setProyecto(proyecto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaRepository.save(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable Long id, @RequestBody Tarea datos) {
        Tarea tareaExistente = tareaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));

        if (datos.getEstado() != null) {
            tareaExistente.setEstado(datos.getEstado());
        }
        if (datos.getTitulo() != null) {
            tareaExistente.setTitulo(datos.getTitulo());
        }

        return ResponseEntity.ok(tareaRepository.save(tareaExistente));
    }
}