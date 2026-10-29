package com.example.examen.controllers;

import com.example.examen.models.Proyecto;
import com.example.examen.services.ProyectoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<Proyecto>> listar(Authentication authentication) {
        return ResponseEntity.ok(proyectoService.listar(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<Proyecto> crear(@RequestBody Proyecto proyecto, Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proyectoService.crear(authentication.getName(), proyecto));
    }
}