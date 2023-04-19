package com.atv.backend.security;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginView {
    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        if (StringUtils.isNotBlank(loginForm.username()) && StringUtils.isNotBlank(loginForm.password())) {
            return "success";
        }
        return "error";
    }

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }
}
