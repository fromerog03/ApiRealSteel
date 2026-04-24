package com.api.realsteel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateRoutineExerciseRequest {

    @NotNull(message = "El exerciseId es obligatorio")
    private Long exerciseId;

    @NotBlank(message = "El día de la semana es obligatorio")
    private String diaSemana;

    private Integer orden;

    // NUEVO: planificación de la rutina
    private Integer seriesObjetivo;
    private Integer repsObjetivo;
    private Double pesoObjetivo;

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }

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
}