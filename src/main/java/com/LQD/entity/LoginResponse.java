package com.LQD.entity;

public class LoginResponse {
    private String token;
    private String username;
    private String Role;

    public LoginResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        Role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
