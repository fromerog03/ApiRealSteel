package com.api.realsteel.service;

import com.api.realsteel.entity.ExerciseEntity;
import com.api.realsteel.entity.RecordEntity;
import com.api.realsteel.entity.SessionEntity;
import com.api.realsteel.error.ResourceNotFoundException;
import com.api.realsteel.repository.ExerciseRepository;
import com.api.realsteel.repository.RecordRepository;
import com.api.realsteel.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final SessionRepository sessionRepository;
    private final ExerciseRepository exerciseRepository;

    public RecordService(RecordRepository recordRepository,
                         SessionRepository sessionRepository,
                         ExerciseRepository exerciseRepository) {
        this.recordRepository = recordRepository;
        this.sessionRepository = sessionRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public RecordEntity createRecord(Long sessionId, Long exerciseId,
                                     Integer numeroSerie, Double peso,
                                     Integer repeticiones, LocalTime horaRegistro,
                                     Boolean completado) {

        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada con id " + sessionId));

        ExerciseEntity exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ResourceNotFoundException("Ejercicio no encontrado con id " + exerciseId));

        RecordEntity record = new RecordEntity();
        record.setSession(session);
        record.setExercise(exercise);
        record.setNumeroSerie(numeroSerie);
        record.setPeso(peso);
        record.setRepeticiones(repeticiones);
        record.setHoraRegistro(horaRegistro);
        record.setCompletado(completado != null ? completado : true);

        return recordRepository.save(record);
    }

    public List<RecordEntity> getRecordsBySession(Long sessionId) {
        sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada con id " + sessionId));
        return recordRepository.findBySession_SessionId(sessionId);
    }

    public List<RecordEntity> getRecordsByUserAndExercise(String userId, Long exerciseId) {
        return recordRepository.findBySession_User_UserIdAndExercise_EjercicioIdOrderBySession_FechaDesc(userId, exerciseId);
    }
}