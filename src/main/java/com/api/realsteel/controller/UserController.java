package com.api.realsteel.controller;

import com.api.realsteel.dto.LoginRequest;
import com.api.realsteel.dto.LoginResponse;
import com.api.realsteel.dto.PasswordResetConfirmRequest;
import com.api.realsteel.dto.PasswordResetRequest;
import com.api.realsteel.dto.UpdateUserRequest;
import com.api.realsteel.dto.UserRequest;
import com.api.realsteel.entity.UserEntity;
import com.api.realsteel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserEntity user = new UserEntity();
        user.setUserId(userRequest.getUserId());
        user.setNombre(userRequest.getNombre());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setGimnasio(userRequest.getGimnasio());
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping({"", "/getAll"})
    public List<UserEntity> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserEntity getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable String id,
                                 @Valid @RequestBody UpdateUserRequest request) {
        UserEntity update = new UserEntity();
        update.setNombre(request.getNombre());
        update.setEmail(request.getEmail());
        update.setPassword(request.getPassword());
        update.setGimnasio(request.getGimnasio());
        return userService.updateUser(id, update);
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<Map<String, String>> requestPasswordReset(
            @Valid @RequestBody PasswordResetRequest request) {
        userService.enviarCodigoRecuperacion(request.getEmail());
        return ResponseEntity.ok(Map.of("message", "Código enviado al email"));
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<Map<String, String>> confirmPasswordReset(
            @Valid @RequestBody PasswordResetConfirmRequest request) {
        userService.confirmarCambioPassword(
                request.getEmail(),
                request.getCodigo(),
                request.getNuevaPassword()
        );
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}