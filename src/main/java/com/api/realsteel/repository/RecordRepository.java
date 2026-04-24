package com.api.realsteel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.realsteel.entity.RecordEntity;

@Repository
public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    // Todos los records de una sesión
    List<RecordEntity> findBySession_SessionId(Long sessionId);

    // NUEVO: historial de un ejercicio concreto para un usuario, ordenado por fecha descendente
    // Esto alimenta las estadísticas de la pantalla de detalle del ejercicio
    List<RecordEntity> findBySession_User_UserIdAndExercise_EjercicioIdOrderBySession_FechaDesc(
            String userId, Long ejercicioId);
}