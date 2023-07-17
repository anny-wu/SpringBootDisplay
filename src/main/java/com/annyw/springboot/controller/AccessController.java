package com.annyw.springboot.controller;

import com.annyw.springboot.bean.Student;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.repo.StudentRepository;
import com.annyw.springboot.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccessController {
    @Autowired
    StudentService studentService;
    
    @GetMapping("/addStudent")
    public String addUser(Model model)
        throws ClassNotFoundException {
        Class<?> cls = Class.forName("com.annyw.springboot.bean.Student");
        Field[] fields = cls.getDeclaredFields();
        List<String> uppercase = new ArrayList<>();
        List<String> lowercase = new ArrayList<>();
        for(int i = 1; i < fields.length-1; i++) {
            lowercase.add(fields[i].getName());
            uppercase.add(fields[i].getName().toUpperCase());
        }
        model.addAttribute("student", new Student());
        model.addAttribute("lowercase",lowercase);
        model.addAttribute("uppercase",uppercase);
        return "add";
    }
    @PostMapping("/addStudent")
    public String handleAdd(Student student, BindingResult bindingResult, Model model)
        throws ClassNotFoundException {
        if (!bindingResult.hasErrors()) {
            if(studentService.studentExists(student.getUsername())){
                model.addAttribute("errorMsg", "user already exists!");
            }
            else {
                studentService.addStudent(student);
                return "redirect:/display";
            }
        }
        Class<?> cls = Class.forName("com.annyw.springboot.bean.Student");
        Field[] fields = cls.getDeclaredFields();
        List<String> uppercase = new ArrayList<>();
        List<String> lowercase = new ArrayList<>();
        for(int i = 1; i < fields.length-1; i++) {
            lowercase.add(fields[i].getName());
            uppercase.add(fields[i].getName().toUpperCase());
        }
        model.addAttribute("student", new Student());
        model.addAttribute("lowercase",lowercase);
        model.addAttribute("uppercase",uppercase);
        return "add";
    }
    @GetMapping("/deleteStudent")
    public String deleteUser(Model model)
        throws ClassNotFoundException, IllegalAccessException {
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        return "delete";
    }
    
    @PostMapping("/deleteStudent")
    public String handleDelete(@RequestParam("id") Long id, Model model)
        throws ClassNotFoundException, IllegalAccessException {
        if (id != null) {
            studentService.deleteStudent(id);
            return "redirect:/display";
        }
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        return "delete";
    }
    @GetMapping("/editStudent")
    public String editUser(Model model)throws ClassNotFoundException, IllegalAccessException {
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        Class<?> cls = Class.forName("com.annyw.springboot.bean.Student");
        Field[] fields = cls.getDeclaredFields();
        List<String> uppercase = new ArrayList<>();
        List<String> lowercase = new ArrayList<>();
        for(int i = 1; i < fields.length-1; i++) {
            lowercase.add(fields[i].getName());
            uppercase.add(fields[i].getName().toUpperCase());
        }
        model.addAttribute("student", new Student());
        model.addAttribute("lowercase",lowercase);
        model.addAttribute("uppercase",uppercase);
        return "edit";
    }
    
    @PostMapping("/editStudent")
    public String handleEdit(@RequestParam("id")int id, Student student, BindingResult bindingResult, Model model)
        throws ClassNotFoundException, IllegalAccessException {
        if (!bindingResult.hasErrors()) {
            if(studentService.studentExists(student.getUsername())){
                model.addAttribute("errorMsg", "user already exists!");
                model.addAttribute("selected", id);
            }
            else {
                studentService.editStudent(student);
                return "redirect:/display";
            }
        }
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        Class<?> cls = Class.forName("com.annyw.springboot.bean.Student");
        Field[] fields = cls.getDeclaredFields();
        List<String> uppercase = new ArrayList<>();
        List<String> lowercase = new ArrayList<>();
        for(int i = 1; i < fields.length-1; i++) {
            lowercase.add(fields[i].getName());
            uppercase.add(fields[i].getName().toUpperCase());
        }
        model.addAttribute("student", new Student());
        model.addAttribute("lowercase",lowercase);
        model.addAttribute("uppercase",uppercase);
        return "edit";
    }
}
