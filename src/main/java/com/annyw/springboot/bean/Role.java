package com.annyw.springboot.bean;

import jakarta.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "role", schema = "Application")
public class Role {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "role_privilege",
        joinColumns = @JoinColumn(name = "roleID"),
        inverseJoinColumns = @JoinColumn(name = "privilegeID"))
    private Collection<Privilege> privileges;
    
    public Role() {
    }
    
    public Role(String name) {
        this.name = name;
    }
    
   
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Collection<? extends Privilege> getPrivileges() {
        return privileges;
    }
    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
