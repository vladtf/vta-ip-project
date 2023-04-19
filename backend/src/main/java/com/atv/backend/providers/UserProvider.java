package com.atv.backend.providers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProvider {

    private final List<String> registeredUsers = new ArrayList<>();

    public List<String> getRegisteredUsers() {
        return registeredUsers;
    }

    public void addRegisteredUser(String username) {
        registeredUsers.add(username);
    }
}
