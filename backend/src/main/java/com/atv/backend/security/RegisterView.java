package com.atv.backend.security;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterView {
    @PostMapping("/register")
    public String login(@RequestBody RegisterForm registerForm) {
        if (StringUtils.isNotBlank(registerForm.username()) && StringUtils.isNotBlank(registerForm.password()) &&
        StringUtils.isNotBlank(registerForm.email()) && StringUtils.isNotBlank(registerForm.phoneNumber())) {
            return "success";
        }
        return "error";
    }

    @GetMapping("/alive")
    public String alive() {
        return "alive";
    }


}
