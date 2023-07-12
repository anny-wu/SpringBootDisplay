package com.annyw.springboot.repo;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.bean.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Object> {
    
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}
