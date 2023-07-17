package com.annyw.springboot.service;

import com.annyw.springboot.bean.*;
import com.annyw.springboot.repo.AttemptsRepository;
import com.annyw.springboot.repo.RoleRepository;
import com.annyw.springboot.repo.TokenRepository;
import com.annyw.springboot.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private AttemptsRepository attemptsRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
  
    
    public User register(User user){
        Collection<Role> roles = new ArrayList<>();
        
        if(user.getPrivilege().equals("admin")){
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
        }
        else{
            roles.add(roleRepository.findByName("ROLE_USER"));
        }
        String username =
            user.getUsername().substring(0, 1).toUpperCase() + user.getUsername().substring(1).toLowerCase();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
        
        Attempts attempt = new Attempts(username);
        attemptsRepository.save(attempt);
        return user;
    }
    
    public User getUserByUsername(String username){
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        return userRepository.findByUsername(username);
    }
    public boolean usernameExists(String username) {
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        return userRepository.findByUsername(username) != null;
    }
    
    public List<Privilege> getUserPrivilege(String role) {
       System.out.println(roleRepository.findByName(role));
       return (List<Privilege>) roleRepository.findByName(role).getPrivileges();
    }
    
    public void deleteUser(User user){
        tokenRepository.deleteById(user.getId());
        attemptsRepository.deleteByUsername(user.getUsername());
        userRepository.delete(user);
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
