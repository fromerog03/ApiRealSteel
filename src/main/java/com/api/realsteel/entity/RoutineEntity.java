package com.api.realsteel.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "routines")
public class RoutineEntity {

    // CAMBIO: antes @Column(name = "id"), ahora apunta a "rutina_id" que es la PK real
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rutina_id")
    private Long rutinaId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    // NUEVO: campo activa para marcar la rutina en uso
    @Column(name = "activa")
    private Boolean activa = true;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<RoutineExerciseEntity> exercises;

    public RoutineEntity() {}

    public Long getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(Long rutinaId) {
        this.rutinaId = rutinaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<RoutineExerciseEntity> getExercises() {
        return exercises;
    }

    public void setExercises(List<RoutineExerciseEntity> exercises) {
        this.exercises = exercises;
    }
}