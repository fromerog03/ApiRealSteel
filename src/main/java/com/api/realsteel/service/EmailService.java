package com.api.realsteel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${brevo.api.key:}")
    private String brevoApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void enviarCodigoReset(String email, String nombre, String codigo) {
        // Log temporal para verificar que la key llega correctamente
        System.out.println("BREVO KEY: " + (brevoApiKey != null && !brevoApiKey.isBlank()
                ? brevoApiKey.substring(0, 10) + "..."
                : "NULL O VACÍA"));

        if (brevoApiKey == null || brevoApiKey.isBlank()) {
            System.out.println("BREVO NO CONFIGURADO — código para " + email + ": " + codigo);
            return;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", brevoApiKey);

        Map<String, Object> body = Map.of(
            "sender", Map.of("name", "RealSteel", "email", "acerodeclaudia@gmail.com"),
            "to", List.of(Map.of("email", email, "name", nombre)),
            "subject", "RealSteel - Código de recuperación",
            "textContent",
                "Hola " + nombre + ",\n\n" +
                "Tu código de recuperación es:\n\n" +
                "  " + codigo + "\n\n" +
                "Introduce este código en la app para cambiar tu contraseña.\n" +
                "El código expira en 15 minutos.\n\n" +
                "Si no solicitaste este cambio, ignora este email.\n\n" +
                "— Equipo RealSteel"
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(
                "https://api.brevo.com/v3/smtp/email",
                request,
                String.class
            );
            System.out.println("Email enviado correctamente a: " + email);
        } catch (Exception e) {
            System.err.println("Error Brevo: " + e.getMessage());
            throw new RuntimeException("Error al enviar el email");
        }
    }
}