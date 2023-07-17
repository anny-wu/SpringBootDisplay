package com.annyw.springboot.bean;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "Application")
public class Student {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "username", nullable = false, length = 24)
    private String username;
    
    @Column(name = "age", nullable = false)
    private int age;
    
    @Column(name = "update_time", nullable = false)
    private Timestamp updateTime = new Timestamp(System.currentTimeMillis());
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}

