package com.annyw.springboot.bean;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "TOKENS", schema = "Application")
public class VerificationToken {
        private static final int EXPIRATION = 60*24;
        
        @Id
        @Column(name = "id", nullable = false)
        private Long id;
    
        @Column(name = "tokens", nullable = false)
        private String token;
        
        @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(nullable = false, name = "id")
        private User user;
    
        @Column(name = "expired_date", nullable = false)
        private Date expiredDate;
        
    public VerificationToken() {
    
    }
    
    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.id = user.getId();
        setExpiredDate();
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Date getExpiredDate() {return expiredDate;}
    
    public void setExpiredDate(){
        this.expiredDate = calculateExpiredDate(EXPIRATION);
    }
    
    private Date calculateExpiredDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}

