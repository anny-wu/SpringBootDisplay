package com.annyw.springboot.repo;

import com.annyw.springboot.bean.Attempts;
import com.annyw.springboot.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AttemptsRepository extends JpaRepository<Attempts, Object> {
    Attempts findAttemptsByUsername(String username);
    void deleteByUsername(String username);
}
