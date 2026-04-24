package com.api.realsteel.service;

import com.api.realsteel.entity.RoutineEntity;
import com.api.realsteel.entity.UserEntity;
import com.api.realsteel.error.ResourceNotFoundException;
import com.api.realsteel.repository.RoutineRepository;
import com.api.realsteel.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    public RoutineService(RoutineRepository routineRepository, UserRepository userRepository) {
        this.routineRepository = routineRepository;
        this.userRepository = userRepository;
    }

    public RoutineEntity createRoutine(String userId, RoutineEntity routine) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + userId));
        routine.setUser(user);
        routine.setFechaCreacion(LocalDateTime.now());
        routine.setActiva(true);
        return routineRepository.save(routine);
    }

    public List<RoutineEntity> getAllRoutines() {
        return routineRepository.findAll();
    }

    public List<RoutineEntity> getUserRoutines(String userId) {
        return routineRepository.findByUser_UserId(userId);
    }

    public RoutineEntity getRoutineById(Long id) {
        return routineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rutina no encontrada con id " + id));
    }

    public RoutineEntity updateRoutine(Long id, RoutineEntity update) {
        RoutineEntity existing = getRoutineById(id);
        if (update.getNombre() != null) existing.setNombre(update.getNombre());
        if (update.getDescripcion() != null) existing.setDescripcion(update.getDescripcion());
        if (update.getActiva() != null) existing.setActiva(update.getActiva());
        return routineRepository.save(existing);
    }

    public void deleteRoutine(Long id) {
        RoutineEntity existing = getRoutineById(id);
        routineRepository.delete(existing);
    }
}