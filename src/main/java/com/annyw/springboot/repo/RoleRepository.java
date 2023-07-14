package com.annyw.springboot.repo;

import com.annyw.springboot.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role, Object> {
    Role findByName(String name);
}
