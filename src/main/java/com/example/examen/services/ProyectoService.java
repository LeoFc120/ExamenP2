package com.example.examen.services;

import com.example.examen.models.Proyecto;
import com.example.examen.models.Usuario;
import com.example.examen.repositories.ProyectoRepository;
import com.example.examen.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProyectoService {
    private static final int LIMITE_PROYECTOS = 3;

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Proyecto> listar(String username) {
        return proyectoRepository.findByUsuario(buscarUsuario(username));
    }

    public Proyecto crear(String username, Proyecto proyecto) {
        if (proyecto.getNombre() == null || proyecto.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del proyecto es obligatorio");
        }

        Usuario usuario = buscarUsuario(username);

        // Regla de negocio: máximo 3 proyectos por usuario
        if (proyectoRepository.countByUsuario(usuario) >= LIMITE_PROYECTOS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Alcanzaste el límite de 3 proyectos por usuario");
        }

        proyecto.setId(null);
        proyecto.setUsuario(usuario);
        return proyectoRepository.save(proyecto);
    }

    private Usuario buscarUsuario(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
}