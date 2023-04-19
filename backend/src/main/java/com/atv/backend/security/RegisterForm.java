package com.atv.backend.security;

public record RegisterForm(String username, String password, String email, String phoneNumber) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phoneNumber;
    }
}
