package com.annyw.springboot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.annyw.springboot.bean.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
}
