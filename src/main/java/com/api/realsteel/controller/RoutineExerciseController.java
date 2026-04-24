package com.api.realsteel.controller;

import com.api.realsteel.dto.CreateRoutineExerciseRequest;
import com.api.realsteel.entity.RoutineExerciseEntity;
import com.api.realsteel.service.RoutineExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine-exercises")
@CrossOrigin
public class RoutineExerciseController {

    private final RoutineExerciseService routineExerciseService;

    public RoutineExerciseController(RoutineExerciseService routineExerciseService) {
        this.routineExerciseService = routineExerciseService;
    }

    // Añadir ejercicio a un día de la rutina
    @PostMapping("/routine/{routineId}")
    public ResponseEntity<RoutineExerciseEntity> addExerciseToRoutine(
            @PathVariable Long routineId,
            @Valid @RequestBody CreateRoutineExerciseRequest request) {

        RoutineExerciseEntity created = routineExerciseService.addExerciseToRoutine(
                routineId,
                request.getExerciseId(),
                request.getDiaSemana(),
                request.getOrden(),
                request.getSeriesObjetivo(),
                request.getRepsObjetivo(),
                request.getPesoObjetivo());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Todos los ejercicios de una rutina (todos los días)
    @GetMapping("/routine/{routineId}")
    public List<RoutineExerciseEntity> getByRoutine(@PathVariable Long routineId) {
        return routineExerciseService.getByRoutineId(routineId);
    }

    // NUEVO: ejercicios de una rutina filtrados por día (para el calendario)
    @GetMapping("/routine/{routineId}/dia/{diaSemana}")
    public List<RoutineExerciseEntity> getByRoutineAndDia(@PathVariable Long routineId,
                                                           @PathVariable String diaSemana) {
        return routineExerciseService.getByRoutineIdAndDia(routineId, diaSemana);
    }

    // NUEVO: eliminar un ejercicio de la rutina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutineExercise(@PathVariable Long id) {
        routineExerciseService.deleteRoutineExercise(id);
        return ResponseEntity.noContent().build();
    }
}