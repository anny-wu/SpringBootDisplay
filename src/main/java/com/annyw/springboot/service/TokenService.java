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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
    
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }
    
    public User generateNewVerificationToken(String existingToken) {
        VerificationToken token = tokenRepository.findByToken(existingToken);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiredDate();
        tokenRepository.save(token);
        return token.getUser();
    }
}
