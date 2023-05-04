package com.atv.backend.dao.services;

import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import com.atv.backend.security.LoginForm;
import com.atv.backend.security.RegisterForm;
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

    public User registerUser(RegisterForm registerForm) {
        User user = new User();
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setPassword(registerForm.getPassword());
        user.setEmail(registerForm.getEmail());
        user.setPhoneNumber(registerForm.getPhoneNumber());

        return userRepository.save(user);
    }

    public boolean userExists(LoginForm loginForm) {
        return userRepository.existsByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
    }

    public Token login(LoginForm form) {
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
