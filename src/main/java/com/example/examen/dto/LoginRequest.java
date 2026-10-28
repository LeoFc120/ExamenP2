package com.example.examen.dto;
import lombok.Data;

@Data
public class LoginRequest {
    private String username; // El examen pide username, la chuleta decía correo
    private String password;
}