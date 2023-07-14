package com.annyw.springboot.controller;

import com.annyw.springboot.bean.CustomUserDetails;
import com.annyw.springboot.bean.Student;
import com.annyw.springboot.repo.StudentRepository;
import com.annyw.springboot.repo.UserRepository;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    StudentService studentService;
    @PostMapping("/display")
    public String successfullyLogin() {
        return "filtered";
    }
    @GetMapping("/display")
    public String login(HttpServletRequest request, Model model)
        throws UnsupportedEncodingException, ClassNotFoundException, IllegalAccessException {
        request.setCharacterEncoding("UTF-8");
        System.out.println(studentService.getCount());
        model.addAttribute("count", studentService.getCount());
        model.addAttribute("pageS",3);
        
        //Store options for the number of rows displayed per page
        Map<String, Integer> values = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            values.put(String.valueOf(i), i);
        }
        model.addAttribute("values", values);
        List<String> fields = studentService.getFieldName("com.annyw.springboot.bean.Student");
        model.addAttribute("fields", fields);
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        return "filtered";
    }
    
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
    @GetMapping("/home")
    public String loginGet(HttpServletRequest request, Model model,
        @RequestParam(value = "error", required = false) boolean error){
        if(error == true) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage",getErrorMessage(request, WebAttributes.AUTHENTICATION_EXCEPTION));
        }
        else{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                model.addAttribute("username", authentication.getName());
            }
        }
        return "login";
    }
    
    @PostMapping("/home")
    public String loginPost(){
        return "login";
    }
    
    @GetMapping("/admin")
    public String adminAccess(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Collection<String> roles = userDetails.getRoles();
            if(roles.contains("ROLE_ADMIN")){
                return "redirect:/display";
            }
            else {
                model.addAttribute("username", authentication.getName());
                model.addAttribute("errorMessage", "You don't have admin privilege.");
            }
            
        }
        return "login";
    }
   
    @PostMapping("/logout")
    public String logout(){
        return "login";
    }
    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof LockedException) {
            error = exception.getMessage();
        }
        else if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        }
        else if (exception instanceof DisabledException){
            error = "User account is disabled! Please verify your email first!";
        }
        else{
            error = "Unknown errors";
        }
        return error;
    }
    
}
