package com.example.examen.repositories;

import com.example.examen.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}