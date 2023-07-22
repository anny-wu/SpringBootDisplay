package com.annyw.springboot.bean;

import com.annyw.springboot.annotations.UniqueUsername;
import com.annyw.springboot.validator.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Collection;

@Entity
@Table(name = "user", schema = "Application")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "email", nullable = false, length = 50)
    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$",
        message = "Email should be valid", groups = EmailPattern.class)
    private String email;
    
    @Column(name = "username", unique = true, nullable = false, length = 24)
    @NotBlank(message = "Username must not be blank", groups = UsernameNotBlank.class)
    @Pattern(regexp = "^\\w+$", message = "Username must contains only alphanumeric characters", groups =
        UsernamePattern.class)
    @UniqueUsername(groups = UsernameValidator.class)
    private String username;
    
    @Column(name = "password", nullable = false, length = 75)
    @NotBlank(message = "Password must not be blank", groups = PasswordNotBlank.class)
    @Size(min = 8, max = 12, message = "Password must be 8-12 characters long", groups = LengthReq.class)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,12}$", message = "It must contains" +
        " one uppercase, one lowercase, one number and one special character", groups =
        PasswordPattern.class)
    private String password;
    
    @Transient
    private String privilege;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "userID"),
        inverseJoinColumns = @JoinColumn(name = "roleID")
    )
    private Collection<Role> roles;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;
    
    @Column(name = "account_nonexpired", nullable = false)
    private boolean accountNonExpired = true;
    
    @Column(name = "account_nonlocked", nullable = false)
    private boolean accountNonLocked = true;
    
    @Column(name = "credentials_nonexpired", nullable = false)
    private boolean credentialsNonExpired = true;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email){this.email = email;}
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPrivilege() {
        return privilege;
    }
    
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
    
    public Collection<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
}
