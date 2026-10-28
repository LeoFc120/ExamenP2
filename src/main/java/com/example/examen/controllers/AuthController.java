package com.example.examen.controllers;
import com.example.examen.dto.AuthResponse;
import com.example.examen.dto.LoginRequest;
import com.example.examen.models.Usuario;
import com.example.examen.repositories.UsuarioRepository;
import com.example.examen.services.JwtService;
import com.example.examen.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElse(null);
        if (usuario == null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
        return ResponseEntity.ok(new AuthResponse(jwtService.generarToken(usuario)));
    }
}