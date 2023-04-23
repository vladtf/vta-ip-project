package com.atv.backend.dao.services;

import com.atv.backend.dao.entities.User;
import com.atv.backend.dao.repositories.UserRepository;
import com.atv.backend.security.LoginForm;
import com.atv.backend.security.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterForm registerForm) {
        User user = new User();
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setPassword(registerForm.getPassword());
        user.setEmail(registerForm.getEmail());
        user.setPhoneNumber(registerForm.getPhoneNumber());

        userRepository.save(user);
    }

    public boolean login(LoginForm form) {
        return userRepository.existsByEmailAndPassword(form.getEmail(), form.getPassword());
    }

    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<String> getRegisteredUsers() {
        return userRepository.findAll().stream().map(User::getEmail).toList();
    }
}
