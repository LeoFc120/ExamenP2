package com.example.examen.services;

import com.example.examen.models.Proyecto;
import com.example.examen.models.Tarea;
import com.example.examen.models.enums.EstadoTarea;
import com.example.examen.repositories.ProyectoRepository;
import com.example.examen.repositories.TareaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public Tarea crear(String username, Tarea tarea) {
        if (tarea.getTitulo() == null || tarea.getTitulo().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El título de la tarea es obligatorio");
        }
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el id del proyecto");
        }

        // El proyecto debe existir y pertenecer al usuario del token
        Proyecto proyecto = proyectoRepository.findById(tarea.getProyecto().getId())
                .filter(p -> p.getUsuario().getUsername().equals(username))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proyecto no encontrado"));

        tarea.setId(null);
        tarea.setProyecto(proyecto);
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarEstado(String username, Long id, EstadoTarea estado) {
        if (estado == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe enviar el estado de la tarea");
        }

        Tarea tarea = tareaRepository.findById(id)
                .filter(t -> t.getProyecto().getUsuario().getUsername().equals(username))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada"));

        tarea.setEstado(estado);
        return tareaRepository.save(tarea);
    }
}