package com.annyw.springboot.service;

import com.annyw.springboot.bean.Student;
import com.annyw.springboot.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    
    //Get the total number of students
    public long getCount(){
        return studentRepository.count();
    }
    
    //Get field names from Student class as a list
    public List<String> getFieldName(String className)
        throws ClassNotFoundException {
        List<String> fieldName = new ArrayList<>();
        Class<?> cls;
        cls = Class.forName("com.annyw.springboot.bean.Student");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            fieldName.add(field.getName().toUpperCase());
        }
        return fieldName;
    }
    
    //Get user details for display
    public List<List<Object>> getStudentList()
        throws ClassNotFoundException, IllegalAccessException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        List<Student> students = studentRepository.findAll();
        System.out.println(students);
        Class<?> cls;
        cls = Class.forName("com.annyw.springboot.bean.Student");
        //Get users from database
        Field[] fields = cls.getDeclaredFields();
        
        List<List<Object>> result = new ArrayList<>();
        for (Student s : students) {
            List<Object> temp = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                Object obj = field.get(s);
                
                if (obj.getClass().getName().equals("java.sql.Timestamp")) {
                    temp.add(sdf.format(obj));
                }
                else {
                    temp.add(obj);
                }
            }
            result.add(temp);
        }
        return result;
    }
}

