package com.annyw.springboot.repo;


import com.annyw.springboot.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findAll();
    Student findByUsername(String username);
    void deleteById(Long id);
}
