package com.example.examen.controllers;

import com.example.examen.models.Proyecto;
import com.example.examen.models.Usuario;
import com.example.examen.repositories.ProyectoRepository;
import com.example.examen.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> crearProyecto(@RequestBody Proyecto proyecto, Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));


        if (proyectoRepository.countByUsuario(usuario) >= 3) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body("Límite de proyectos: Un usuario no puede tener más de 3 proyectos activos.");
        }

        proyecto.setUsuario(usuario);
        return ResponseEntity.status(CREATED).body(proyectoRepository.save(proyecto));
    }

    @GetMapping
    public ResponseEntity<List<Proyecto>> listarMisProyectos(Authentication authentication) {
        String username = authentication.getName();
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario no encontrado"));

        return ResponseEntity.ok(proyectoRepository.findByUsuario(usuario));
    }
}