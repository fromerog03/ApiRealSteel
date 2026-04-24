package com.api.realsteel.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateRecordRequest {

    // NUEVO: número de serie obligatorio (1, 2, 3, 4...)
    @NotNull(message = "El número de serie es obligatorio")
    @Min(value = 1, message = "El número de serie debe ser al menos 1")
    private Integer numeroSerie;

    @NotNull(message = "El peso es obligatorio")
    private Double peso;

    @NotNull(message = "Las repeticiones son obligatorias")
    private Integer repeticiones;

    private LocalTime horaRegistro;

    private Boolean completado = true;

    @NotNull(message = "El exerciseId es obligatorio")
    private Long exerciseId;

    public Integer getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(Integer numeroSerie) { this.numeroSerie = numeroSerie; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Integer getRepeticiones() { return repeticiones; }
    public void setRepeticiones(Integer repeticiones) { this.repeticiones = repeticiones; }

    public LocalTime getHoraRegistro() { return horaRegistro; }
    public void setHoraRegistro(LocalTime horaRegistro) { this.horaRegistro = horaRegistro; }

    public Boolean getCompletado() { return completado; }
    public void setCompletado(Boolean completado) { this.completado = completado; }

    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }
}