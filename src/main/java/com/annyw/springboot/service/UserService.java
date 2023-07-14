package com.annyw.springboot.service;

import com.annyw.springboot.bean.Role;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.bean.VerificationToken;
import com.annyw.springboot.repo.RoleRepository;
import com.annyw.springboot.repo.TokenRepository;
import com.annyw.springboot.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
  
    
    public User register(User user){
        Collection<Role> roles = new ArrayList<>();
        
        if(user.getPrivilege() == "admin"){
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
        }
        else{
            Role role = new Role("USER");
            roles.add(roleRepository.findByName("ROLE_USER"));
        }
        user.setUsername(user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1).toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }
    public boolean usernameExists(String username) {
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        return userRepository.findByUsername(username) != null;
    }
    
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }
    
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }
    
    public User generateNewVerificationToken(String existingToken) {
        VerificationToken token = tokenRepository.findByToken(existingToken);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiredDate();
        tokenRepository.save(token);
        return token.getUser();
    }
}
