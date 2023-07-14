package com.annyw.springboot.security;
import com.annyw.springboot.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.annyw.springboot.bean.Attempts;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.repo.AttemptsRepository;
import com.annyw.springboot.repo.UserRepository;

/*@Component
public class AuthProvider implements AuthenticationProvider {
    private static final int ATTEMPTS_LIMIT = 3;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private AttemptsRepository attemptsRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        String username = authentication.getName();
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        Attempts attempts = attemptsRepository.findByUsername(username);
        if (attempts.getAttempts() != 0) {
            attempts.setAttempts(0);
            attemptsRepository.save(attempts);
        }
        return authentication;
    }
    private void processFailedAttempts(String username, User user) {
        if (user.isAccountNonLocked()) {
            username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
            Attempts attempts = attemptsRepository.findByUsername(username);
            if (attempts.getAttempts() == 0) {
                attempts.setAttempts(1);
                attemptsRepository.save(attempts);
            }
            else {
                if (attempts.getAttempts() + 1 > ATTEMPTS_LIMIT) {
                    user.setAccountNonLocked(false);
                    userRepository.save(user);
                    throw new LockedException("Too many invalid attempts. Account is locked!!");
                }
                
                attempts.setAttempts(attempts.getAttempts() + 1);
                attemptsRepository.save(attempts);
            }
        }
        else {
            throw new LockedException("Account has been locked! Please reset your password to unlock.");
        }
    }
    
    @Override public boolean supports(Class<?> authentication) {
        return true;
    }
}*/
