package com.example.examen.repositories;

import com.example.examen.models.Proyecto;
import com.example.examen.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    int countByUsuario(Usuario usuario);
    List<Proyecto> findByUsuario(Usuario usuario);
}