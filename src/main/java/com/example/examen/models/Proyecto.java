package com.example.examen.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "proyectos")
@Getter
@Setter
@NoArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "proyecto")
    @JsonIgnore
    private List<Tarea> tareas;
}
