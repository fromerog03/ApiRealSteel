package com.api.realsteel.controller;

import com.api.realsteel.dto.CreateRecordRequest;
import com.api.realsteel.entity.RecordEntity;
import com.api.realsteel.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@CrossOrigin
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    // Guardar una serie (peso + reps + número de serie)
    @PostMapping("/session/{sessionId}")
    public ResponseEntity<RecordEntity> createRecord(@PathVariable Long sessionId,
                                                     @Valid @RequestBody CreateRecordRequest request) {
        RecordEntity created = recordService.createRecord(
                sessionId,
                request.getExerciseId(),
                request.getNumeroSerie(),
                request.getPeso(),
                request.getRepeticiones(),
                request.getHoraRegistro(),
                request.getCompletado());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Todos los records de una sesión
    @GetMapping("/session/{sessionId}")
    public List<RecordEntity> getSessionRecords(@PathVariable Long sessionId) {
        return recordService.getRecordsBySession(sessionId);
    }

    // NUEVO: historial de un ejercicio para un usuario (para las estadísticas)
    @GetMapping("/user/{userId}/exercise/{exerciseId}")
    public List<RecordEntity> getHistorial(@PathVariable String userId,
                                           @PathVariable Long exerciseId) {
        return recordService.getRecordsByUserAndExercise(userId, exerciseId);
    }
}