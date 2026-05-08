package com.api.realsteel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordResetConfirmRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String codigo;

    @NotBlank
    @Size(min = 6)
    private String nuevaPassword;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNuevaPassword() { return nuevaPassword; }
    public void setNuevaPassword(String nuevaPassword) { this.nuevaPassword = nuevaPassword; }
}