package com.api.realsteel.dto;

// Lo que devolvemos al Flutter cuando el login es correcto.
// El Flutter guarda el userId y el nombre para usarlos en la app.
public class LoginResponse {

    private String userId;
    private String nombre;
    private String email;
    private String gimnasio;

    public LoginResponse(String userId, String nombre, String email, String gimnasio) {
        this.userId = userId;
        this.nombre = nombre;
        this.email = email;
        this.gimnasio = gimnasio;
    }

    public String getUserId() { return userId; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getGimnasio() { return gimnasio; }
}