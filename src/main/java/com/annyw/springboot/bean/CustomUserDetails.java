package com.annyw.springboot.bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class CustomUserDetails implements UserDetails {
    
    private User user;
    
    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }
    
    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> roles = user.getRoles();
        Set<String> privileges = new HashSet<>();
        for (Role role : roles) {
            for(Privilege item : role.getPrivileges()){
                privileges.add(item.getName());
            }
        }
        
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    
    public Collection<String> getRoles(){
        Collection<String> result = new HashSet<>();
        for(Role role : user.getRoles()){
            result.add(role.getName());
        }
        return result;
    }
    
    public String getEmail() {
        return user.getEmail();
    }
    
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }
    
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
