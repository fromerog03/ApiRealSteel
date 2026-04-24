package com.api.realsteel.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "records")
public class RecordEntity {

    // CAMBIO: antes @Column(name = "id"), ahora apunta a "record_id" que es la PK real
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long recordId;

    // NUEVO: número de serie (1ª serie, 2ª serie, 3ª serie...)
    // Esencial para el seguimiento de entrenamiento
    @Column(name = "numero_serie", nullable = false)
    private Integer numeroSerie;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "repeticiones")
    private Integer repeticiones;

    @Column(name = "hora_registro")
    private LocalTime horaRegistro;

    @Column(name = "completado")
    private Boolean completado = true;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private SessionEntity session;

    // CAMBIO: antes @JoinColumn(name = "exercise_id") que era la columna duplicada.
    // Ahora apunta a "ejercicio_id" que es la FK correcta.
    @ManyToOne
    @JoinColumn(name = "ejercicio_id", nullable = false)
    private ExerciseEntity exercise;

    public RecordEntity() {}

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Integer getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(Integer repeticiones) {
        this.repeticiones = repeticiones;
    }

    public LocalTime getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(LocalTime horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public SessionEntity getSession() {
        return session;
    }

    public void setSession(SessionEntity session) {
        this.session = session;
    }

    public ExerciseEntity getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseEntity exercise) {
        this.exercise = exercise;
    }
}