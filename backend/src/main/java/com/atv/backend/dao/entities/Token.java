package com.atv.backend.dao.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    private String device;

    private Date expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {
    }

    public Token(String device, User user) {
        this.device = device;
        this.user = user;
        LocalDate currentDate = LocalDate.now();
        LocalDate expiryDate = currentDate.plusDays(3);
        this.expiresAt = Date.valueOf(expiryDate);

        this.token = UUID.randomUUID().toString();
    }

    public Integer getId()  {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
