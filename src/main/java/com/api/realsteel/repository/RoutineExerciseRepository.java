package com.api.realsteel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.realsteel.entity.RoutineExerciseEntity;

@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExerciseEntity, Long> {

    // Todos los ejercicios de una rutina
    List<RoutineExerciseEntity> findByRoutine_RutinaId(Long rutinaId);

    // NUEVO: ejercicios de una rutina filtrados por día (ej: todos los del Lunes)
    List<RoutineExerciseEntity> findByRoutine_RutinaIdAndDiaSemana(Long rutinaId, String diaSemana);
}