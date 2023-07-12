package com.annyw.springboot.service;

import com.annyw.springboot.bean.Role;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.bean.VerificationToken;
import com.annyw.springboot.repo.TokenRepository;
import com.annyw.springboot.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    
    public User register(User user){
        String username = user.getUsername();
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        if(user.getPrivilege() == "admin"){
            Role role = new Role("ADMIN");
            user.addRole(role);
        }
        else{
            Role role = new Role("USER");
            user.addRole(role);
        }
        return userRepository.save(user);
    }
    public boolean usernameExists(String username) {
        return userRepository.findUserByUsername(username) != null;
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
}
