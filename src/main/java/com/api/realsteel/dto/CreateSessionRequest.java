package com.api.realsteel.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;

public class CreateSessionRequest {

    // CAMBIO: antes era LocalDateTime fecha. Ahora es LocalDate + hora inicio/fin separadas
    @NotNull(message = "La fecha de la sesión es obligatoria")
    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private Long rutinaId;

    private Integer calorias;

    private String notas;

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public Long getRutinaId() { return rutinaId; }
    public void setRutinaId(Long rutinaId) { this.rutinaId = rutinaId; }

    public Integer getCalorias() { return calorias; }
    public void setCalorias(Integer calorias) { this.calorias = calorias; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}