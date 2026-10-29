package com.example.examen.controllers;

import com.example.examen.dto.AuthResponse;
import com.example.examen.dto.LoginRequest;
import com.example.examen.models.Usuario;
import com.example.examen.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioService.registrar(request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado: " + usuario.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(new AuthResponse(usuarioService.login(request.getUsername(), request.getPassword())));
    }
}