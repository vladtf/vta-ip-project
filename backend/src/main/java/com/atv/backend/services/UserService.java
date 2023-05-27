package com.atv.backend.services;

import com.atv.backend.dao.entities.Token;
import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.TokenRepository;
import com.atv.backend.dao.repositories.UserRepository;
import com.atv.backend.requests.LoginRequest;
import com.atv.backend.requests.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
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

        String device = form.getDevice();
        Token token = new Token(device, user);

        String text = MessageFormat.format("<html><body><p>You have logged in from {0} at {1}. Your token is {2}</p><p>Follow this link to authenticate to your account: <a href=\"{3}\">Activate</a></p></body></html>",
                device, new Date(), token.getToken(), device + "/activate/" + token.getToken());

        emailService.sendHtmlMessage(user.getEmail(), "Login", text);

        return tokenRepository.save(token);
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<String> getRegisteredUsers() {
        return userRepository.findAll().stream().map(User::getEmail).toList();
    }

    public List<String> getAllEmails(String token) {
        List<User> all = userRepository.findAll();
        return all.stream().map(User::getEmail).toList();
    }
}
