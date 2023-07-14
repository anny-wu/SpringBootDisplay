package com.annyw.springboot.repo;

import com.annyw.springboot.bean.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Object> {
    Privilege findByName(String name);
}
