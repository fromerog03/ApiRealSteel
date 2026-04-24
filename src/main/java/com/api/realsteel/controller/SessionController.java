package com.api.realsteel.controller;

import com.api.realsteel.dto.CreateSessionRequest;
import com.api.realsteel.entity.SessionEntity;
import com.api.realsteel.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/sessions")
@CrossOrigin
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // Crear sesión (el usuario llega al gimnasio)
    @PostMapping("/user/{userId}")
    public ResponseEntity<SessionEntity> createSession(@PathVariable String userId,
                                                       @Valid @RequestBody CreateSessionRequest request) {
        SessionEntity created = sessionService.createSession(
                userId,
                request.getFecha(),
                request.getHoraInicio(),
                request.getHoraFin(),
                request.getRutinaId(),
                request.getCalorias(),
                request.getNotas());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // NUEVO: finalizar sesión (el usuario sale del gimnasio — actualiza hora_fin)
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<SessionEntity> finalizarSesion(@PathVariable Long id,
                                                         @RequestParam String horaFin) {
        SessionEntity updated = sessionService.finalizarSesion(id, LocalTime.parse(horaFin));
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/user/{userId}")
    public List<SessionEntity> getSessionsByUser(@PathVariable String userId) {
        return sessionService.getSessionsByUser(userId);
    }

    @GetMapping
    public List<SessionEntity> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id:\\d+}")
    public SessionEntity getSessionById(@PathVariable Long id) {
        return sessionService.getSessionById(id);
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}