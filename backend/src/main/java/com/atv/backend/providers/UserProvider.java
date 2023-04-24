package com.atv.backend.providers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserProvider {

    private final List<String> registeredUsers = new ArrayList<>();

    public List<String> getRegisteredUsers() {
        return registeredUsers;
    }
    public void addRegisteredUser(String username) {
        registeredUsers.add(username);
    }
}
