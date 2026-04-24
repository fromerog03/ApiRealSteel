package com.api.realsteel.service;

import com.api.realsteel.dto.LoginRequest;
import com.api.realsteel.dto.LoginResponse;
import com.api.realsteel.entity.UserEntity;
import com.api.realsteel.error.ResourceNotFoundException;
import com.api.realsteel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    // NUEVO: inyectamos el encoder de BCrypt definido en SecurityConfig
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(UserEntity user) {
        // Comprobamos que el email no esté ya registrado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        // CAMBIO: hasheamos la contraseña antes de guardarla
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFechaRegistro(LocalDateTime.now());
        return userRepository.save(user);
    }

    // NUEVO: método de login
    public LoginResponse login(LoginRequest request) {
        // Buscamos por email
        UserEntity user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("No existe ningún usuario con ese email");
        }
        // Comparamos la contraseña introducida con el hash guardado
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
        // Login correcto: devolvemos los datos que necesita el Flutter
        return new LoginResponse(
                user.getUserId(),
                user.getNombre(),
                user.getEmail(),
                user.getGimnasio()
        );
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + id));
    }

    public UserEntity updateUser(String id, UserEntity update) {
        UserEntity existing = getUserById(id);
        if (update.getNombre() != null) existing.setNombre(update.getNombre());
        if (update.getEmail() != null) existing.setEmail(update.getEmail());
        if (update.getGimnasio() != null) existing.setGimnasio(update.getGimnasio());
        // Si actualiza la contraseña también la hasheamos
        if (update.getPassword() != null && !update.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(update.getPassword()));
        }
        return userRepository.save(existing);
    }

    public void deleteUser(String id) {
        UserEntity existing = getUserById(id);
        userRepository.delete(existing);
    }
}