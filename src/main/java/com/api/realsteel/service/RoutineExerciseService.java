package com.api.realsteel.service;

import com.api.realsteel.entity.ExerciseEntity;
import com.api.realsteel.entity.RoutineEntity;
import com.api.realsteel.entity.RoutineExerciseEntity;
import com.api.realsteel.error.ResourceNotFoundException;
import com.api.realsteel.repository.ExerciseRepository;
import com.api.realsteel.repository.RoutineExerciseRepository;
import com.api.realsteel.repository.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineExerciseService {

    private final RoutineExerciseRepository routineExerciseRepository;
    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;

    public RoutineExerciseService(RoutineExerciseRepository routineExerciseRepository,
                                  RoutineRepository routineRepository,
                                  ExerciseRepository exerciseRepository) {
        this.routineExerciseRepository = routineExerciseRepository;
        this.routineRepository = routineRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public RoutineExerciseEntity addExerciseToRoutine(Long routineId, Long exerciseId,
                                                      String diaSemana, Integer orden,
                                                      Integer seriesObjetivo, Integer repsObjetivo,
                                                      Double pesoObjetivo) {

        RoutineEntity routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Rutina no encontrada con id " + routineId));

        ExerciseEntity exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Ejercicio no encontrado con id " + exerciseId));

        RoutineExerciseEntity entity = new RoutineExerciseEntity();
        entity.setRoutine(routine);
        entity.setExercise(exercise);
        entity.setDiaSemana(diaSemana);
        entity.setOrden(orden);
        entity.setSeriesObjetivo(seriesObjetivo);
        entity.setRepsObjetivo(repsObjetivo);
        entity.setPesoObjetivo(pesoObjetivo);

        return routineExerciseRepository.save(entity);
    }

    public List<RoutineExerciseEntity> getByRoutineId(Long routineId) {
        routineRepository.findById(routineId)
                .orElseThrow(() -> new ResourceNotFoundException("Rutina no encontrada con id " + routineId));
        return routineExerciseRepository.findByRoutine_RutinaId(routineId);
    }

    public List<RoutineExerciseEntity> getByRoutineIdAndDia(Long routineId, String diaSemana) {
        return routineExerciseRepository.findByRoutine_RutinaIdAndDiaSemana(routineId, diaSemana);
    }

    public void deleteRoutineExercise(Long id) {
        RoutineExerciseEntity existing = routineExerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ejercicio de rutina no encontrado con id " + id));
        routineExerciseRepository.delete(existing);
    }
}