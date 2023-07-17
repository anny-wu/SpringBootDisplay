package com.annyw.springboot.controller;

import com.annyw.springboot.bean.CustomUserDetails;
import com.annyw.springboot.bean.Privilege;
import com.annyw.springboot.service.StudentService;
import com.annyw.springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String loginGet(HttpServletRequest request, Model model,
        @RequestParam(value = "error", required = false) boolean error){
        if(error) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage",getErrorMessage(request));
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
                return "redirect:/display?admin";
            }
            else {
                model.addAttribute("username", authentication.getName());
                model.addAttribute("errorMessage", "You don't have admin privilege.");
            }
            
        }
        return "login";
    }
    
    @GetMapping("/display")
    public String login(Model model,
        @RequestParam(value="currentPage",required = false) String currentPage, @RequestParam(value="pageS",required =
        false) String pageS, @RequestParam(value = "admin", required = false) String admin)
        throws ClassNotFoundException, IllegalAccessException {
        model.addAttribute("count", studentService.getCount());
        
        //Store options for the number of rows displayed per page
        Map<String, Integer> values = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            values.put(String.valueOf(i), i);
        }
        model.addAttribute("values", values);
        List<String> fields = studentService.getFieldName("com.annyw.springboot.bean.Student");
        model.addAttribute("fields", fields);
        List<List<Object>> students;
        
        if(currentPage == null || currentPage.equals("")) {
            currentPage = "1";
        }
        if(pageS == null) {
            pageS = "3";
        }
        students = studentService.queryStudentsByPage(Integer.parseInt(currentPage), Integer.parseInt(pageS));
        int pageCount =
            studentService.getStudentListByPage(Integer.parseInt(currentPage),Integer.parseInt(pageS)).getTotalPages();
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Collection<String> roles = userDetails.getRoles();
            List<Privilege> privileges;
            if(roles.contains("ROLE_ADMIN") && admin != null){
                privileges = userService.getUserPrivilege("ROLE_ADMIN");
            }
            else {
                privileges = userService.getUserPrivilege("ROLE_USER");
            }
            for(Privilege p: privileges){
            if(p.getName().equals("ADD_PRIVILEGE")){
                    model.addAttribute("add", true);
                }
                if(p.getName().equals("DELETE_PRIVILEGE")){
                    model.addAttribute("delete", true);
                }
                if(p.getName().equals("EDIT_PRIVILEGE")){
                    model.addAttribute("edit", true);
                }
            }
            
        }
        
       
        
        model.addAttribute("students", students);
        model.addAttribute("currentPage",Integer.parseInt(currentPage));
        model.addAttribute("pageS",Integer.parseInt(pageS));
        model.addAttribute("totalCount",pageCount);
        return "filtered";
    }
    
    @PostMapping("/logout")
    public String logout(){
        return "login";
    }
    
    private String getErrorMessage(HttpServletRequest request) {
        Exception exception = (Exception) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String error;
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
