package com.atv.backend.services;

import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import com.atv.backend.requests.LoginRequest;
import com.atv.backend.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User getUserByToken(String token) {
        Token updatedToken = tokenRepository.findByToken(token);
        return updatedToken.getUser();
    }

    public User registerUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());

        return userRepository.save(user);
    }

    public boolean userExists(LoginRequest loginRequest) {
        return userRepository.existsByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
    }

    public Token login(LoginRequest form) {
        if (!userRepository.existsByEmailAndPassword(form.getEmail(), form.getPassword())) {
            return null;
        }

        User user = userRepository.findByEmail(form.getEmail());

        String device = "test"; // todo get device from request
        Token token = new Token(device, user);

        return tokenRepository.save(token);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<String> getRegisteredUsers() {
        return userRepository.findAll().stream().map(User::getEmail).toList();
    }
}
