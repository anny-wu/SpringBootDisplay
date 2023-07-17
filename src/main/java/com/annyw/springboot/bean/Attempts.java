package com.annyw.springboot.bean;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attempts", schema = "Application")
public class Attempts {
    @Id
    @Column(name = "username", nullable = false, length = 24)
    private String username;
    @Column(name = "attempts", nullable = false)
    private int attempts;
    @Column(name = "locked_time", nullable = false)
    private Date lockedTime; //The time when the user account is locked
    public Attempts(){
    
    }
    
    public Attempts(String username){
        this.username = username;
        this.attempts = 0;
        this.lockedTime = null;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getAttempts() {
        return attempts;
    }
    
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
    
    public Date getLockedTime() {
        return lockedTime;
    }
    
    public void setLockedTime(Date lockedTime) {
        this.lockedTime = lockedTime;
    }
}
