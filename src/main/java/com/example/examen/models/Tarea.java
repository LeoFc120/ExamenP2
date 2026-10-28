package com.example.examen.models;

import com.example.examen.models.enums.EstadoTarea;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tareas")
@Getter
@Setter
@NoArgsConstructor
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoTarea estado;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;
}