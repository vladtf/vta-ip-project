package com.atv.backend.requests;

public class LoginRequest {
    private final String email;
    private final String password;

    private final String device;

    public LoginRequest(String email, String password, String device) {
        this.email = email;
        this.password = password;
        this.device = device;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDevice() {
        return device;
    }
}
