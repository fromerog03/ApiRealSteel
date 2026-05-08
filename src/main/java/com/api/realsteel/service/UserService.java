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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // Mapa en memoria para códigos de recuperación {email -> codigo}
    private final Map<String, String> codigosReset = new ConcurrentHashMap<>();

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public UserEntity createUser(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFechaRegistro(LocalDateTime.now());
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("No existe ningún usuario con ese email");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }
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
        if (update.getPassword() != null && !update.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(update.getPassword()));
        }
        return userRepository.save(existing);
    }

    public void deleteUser(String id) {
        UserEntity existing = getUserById(id);
        userRepository.delete(existing);
    }

    public void enviarCodigoRecuperacion(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("No existe ningún usuario con ese email");
        }
        String codigo = String.format("%06d", new java.util.Random().nextInt(999999));
        codigosReset.put(email, codigo);
        emailService.enviarCodigoReset(email, user.getNombre(), codigo);
    }

    public void confirmarCambioPassword(String email, String codigo, String nuevaPassword) {
        final String codigoGuardado = codigosReset.get(email);
        if (codigoGuardado == null || !codigoGuardado.equals(codigo)) {
            throw new IllegalArgumentException("Código incorrecto o expirado");
        }
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        user.setPassword(passwordEncoder.encode(nuevaPassword));
        userRepository.save(user);
        codigosReset.remove(email);
    }
}