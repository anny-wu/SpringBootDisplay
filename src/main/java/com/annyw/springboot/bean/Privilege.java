package com.annyw.springboot.bean;

import com.annyw.springboot.bean.Role;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "PRIVILEGE", schema = "Application")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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
