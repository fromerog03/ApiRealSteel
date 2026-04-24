package com.api.realsteel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "routine_exercises")
public class RoutineExerciseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "series_objetivo")
    private Integer seriesObjetivo;

    @Column(name = "reps_objetivo")
    private Integer repsObjetivo;

    @Column(name = "peso_objetivo")
    private Double pesoObjetivo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rutina_id", nullable = false)
    private RoutineEntity routine;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id", nullable = false)
    private ExerciseEntity exercise;

    public RoutineExerciseEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Integer getSeriesObjetivo() { return seriesObjetivo; }
    public void setSeriesObjetivo(Integer seriesObjetivo) { this.seriesObjetivo = seriesObjetivo; }

    public Integer getRepsObjetivo() { return repsObjetivo; }
    public void setRepsObjetivo(Integer repsObjetivo) { this.repsObjetivo = repsObjetivo; }

    public Double getPesoObjetivo() { return pesoObjetivo; }
    public void setPesoObjetivo(Double pesoObjetivo) { this.pesoObjetivo = pesoObjetivo; }

    public RoutineEntity getRoutine() { return routine; }
    public void setRoutine(RoutineEntity routine) { this.routine = routine; }

    public ExerciseEntity getExercise() { return exercise; }
    public void setExercise(ExerciseEntity exercise) { this.exercise = exercise; }
}