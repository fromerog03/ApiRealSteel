package com.api.realsteel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.realsteel.entity.ExerciseEntity;

@Repository
public interface ExerciseRepository extends JpaRepository<ExerciseEntity, Long> {

    List<ExerciseEntity> findByGrupoMuscularIgnoreCase(String grupoMuscular);

    List<ExerciseEntity> findByTipoIgnoreCase(String tipo);
}