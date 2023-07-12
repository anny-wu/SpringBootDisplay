package com.annyw.springboot.bean;

import jakarta.persistence.*;

@Entity
@Table(name = "ROLE", schema = "Application")
public class Role {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    
    public Role() {
    
    }
    
    public Role(String name) {
        this.name = name;
    }
    
   
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
