package com.api.realsteel.service;

import com.api.realsteel.entity.RoutineEntity;
import com.api.realsteel.entity.SessionEntity;
import com.api.realsteel.entity.UserEntity;
import com.api.realsteel.error.ResourceNotFoundException;
import com.api.realsteel.repository.RoutineRepository;
import com.api.realsteel.repository.SessionRepository;
import com.api.realsteel.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    public SessionService(SessionRepository sessionRepository,
                          UserRepository userRepository,
                          RoutineRepository routineRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
    }

    // CAMBIO: firma actualizada con LocalDate + horaInicio + horaFin + notas
    public SessionEntity createSession(String userId, LocalDate fecha,
                                       LocalTime horaInicio, LocalTime horaFin,
                                       Long rutinaId, Integer calorias, String notas) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + userId));

        SessionEntity session = new SessionEntity();
        session.setUser(user);
        session.setFecha(fecha);
        session.setHoraInicio(horaInicio);
        session.setHoraFin(horaFin);
        session.setCalorias(calorias);
        session.setNotas(notas);

        if (rutinaId != null) {
            RoutineEntity routine = routineRepository.findById(rutinaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Rutina no encontrada con id " + rutinaId));
            session.setRoutine(routine);
        }

        return sessionRepository.save(session);
    }

    // NUEVO: actualizar hora_fin cuando el usuario termina el entrenamiento
    public SessionEntity finalizarSesion(Long sessionId, LocalTime horaFin) {
        SessionEntity session = getSessionById(sessionId);
        session.setHoraFin(horaFin);
        return sessionRepository.save(session);
    }

    public List<SessionEntity> getAllSessions() {
        return sessionRepository.findAll();
    }

    public List<SessionEntity> getSessionsByUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + userId));
        return sessionRepository.findByUser_UserId(userId);
    }

    public SessionEntity getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada con id " + sessionId));
    }

    public void deleteSession(Long sessionId) {
        SessionEntity existing = getSessionById(sessionId);
        sessionRepository.delete(existing);
    }
}