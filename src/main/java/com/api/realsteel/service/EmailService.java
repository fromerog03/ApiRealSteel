package com.api.realsteel.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCodigoReset(String email, String nombre, String codigo) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("RealSteel — Código de recuperación");
        message.setText(
            "Hola " + nombre + ",\n\n" +
            "Tu código de recuperación de contraseña es:\n\n" +
            "  " + codigo + "\n\n" +
            "Introduce este código en la app para cambiar tu contraseña.\n" +
            "El código expira en 15 minutos.\n\n" +
            "Si no solicitaste este cambio, ignora este email.\n\n" +
            "— Equipo RealSteel"
        );
        mailSender.send(message);
    }
}