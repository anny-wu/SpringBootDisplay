package com.annyw.springboot.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "ATTEMPTS", schema = "Application")
public class Attempts {
    @Id
    @Column(name = "username", nullable = false, length = 24)
    private String username;
    
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "username")
    private User user;
    
    private int attempts;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public int getAttempts() {
        return attempts;
    }
    
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
