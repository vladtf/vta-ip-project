package com.atv.backend.services;

import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final String mailFrom;

    public EmailService(JavaMailSender emailSender, @Value("${spring.mail.from}") String mailFrom) {
        this.emailSender = emailSender;
        this.mailFrom = mailFrom;
    }


    @SneakyThrows
    public void sendHtmlMessage(String to, String subject, String htmlContent) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailFrom);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        emailSender.send(message);
    }

}

