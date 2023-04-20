package com.atv.backend.security;

public record LoginForm(String username, String password) {

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
