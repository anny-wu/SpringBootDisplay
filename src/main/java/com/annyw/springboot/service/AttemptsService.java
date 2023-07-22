package com.annyw.springboot.service;

import com.annyw.springboot.bean.Attempts;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.repo.AttemptsRepository;
import com.annyw.springboot.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AttemptsService {
    
    public static final int MAX_FAILED_ATTEMPTS = 3;
    
    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours 24 * 60 * 60 * 1000
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttemptsRepository attemptsRepository;
    
    public Attempts getAttemptByUsername(String username){
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        return attemptsRepository.findAttemptsByUsername(username);
    }
    
    public void increaseFailedAttempts(String username) {
        Attempts attempts  = getAttemptByUsername(username);
        attempts.setAttempts(attempts.getAttempts() + 1);
        attemptsRepository.save(attempts);
    }
    
    public void resetFailedAttempts(String username) {
        Attempts attempts  = getAttemptByUsername(username);
        attempts.setAttempts(0);
        attemptsRepository.save(attempts);
    }
    
    public void lock(User user) {
        user.setAccountNonLocked(false);
        String username = user.getUsername();
        Attempts attempts  = getAttemptByUsername(username);
        attempts.setLockedTime(new Date());
        
       userRepository.save(user);
       attemptsRepository.save(attempts);
    }
    
    public boolean unlockWhenTimeExpired(User user) {
        String username = user.getUsername();
        username = username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase();
        Attempts attempts = attemptsRepository.findAttemptsByUsername(username);
        long lockTimeInMillis = attempts.getLockedTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();
        
        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            attempts.setLockedTime(null);
            attempts.setAttempts(0);
            
            userRepository.save(user);
            attemptsRepository.save(attempts);
            
            return true;
        }
        
        return false;
    }
}
