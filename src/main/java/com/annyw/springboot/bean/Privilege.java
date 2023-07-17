package com.annyw.springboot.bean;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "privilege", schema = "Application")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    
    @Column(name="name", nullable = false)
    private String name;
    
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    
    public Privilege() {
    
    }
    
    public Privilege(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
