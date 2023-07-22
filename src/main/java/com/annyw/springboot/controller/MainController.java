package com.annyw.springboot.controller;

import com.annyw.springboot.bean.CustomUserDetails;
import com.annyw.springboot.bean.Privilege;
import com.annyw.springboot.service.StudentService;
import com.annyw.springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    StudentService studentService;
    
    @Autowired
    UserService userService;
    
    //Redirect to the log in page
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
    
    //Log in page for all users
    @GetMapping("/home")
    public String loginGet(HttpServletRequest request, Model model,
        @RequestParam(value = "error", required = false) boolean error) {
        //If there is an error, display the error message
        if (error) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", getErrorMessage(request));
        }
        else {
            //Display the user logged in this session
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                model.addAttribute("username", authentication.getName());
            }
        }
        return "login";
    }
    
    //User tries to log in as admin
    @GetMapping("/admin")
    public String adminAccess(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            Collection<String> roles = userDetails.getRoles();
            //If the user has admin privilege, redirect to the admin page
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/display?admin";
            }
            else {
                //If the user does not have admin privilege, display error message
                model.addAttribute("username", authentication.getName());
                model.addAttribute("errorMessage", "You don't have admin privilege.");
            }
            
        }
        return "login";
    }
    
    //Display page for student database
    @GetMapping("/display")
    public String getDisplay(Model model,
        @RequestParam(value = "currentPage", required = false) String currentPage,
        @RequestParam(value = "pageS", required =
            false) String pageS, @RequestParam(value = "admin", required = false) String admin)
        throws ClassNotFoundException, IllegalAccessException {
        //Handle null currentPage parameter
        if (currentPage == null || currentPage.equals("")) {
            currentPage = "1";
        }
        model.addAttribute("currentPage", Integer.parseInt(currentPage));
        //Handle null pageS parameter
        if (pageS == null || pageS.equals("")) {
            pageS = "3";
        }
        model.addAttribute("pageS", Integer.parseInt(pageS));
        
        //Store the total number of students
        model.addAttribute("count", studentService.getCount());
        
        //Store options for the number of rows displayed per page
        Map<String, Integer> values = new HashMap<>();
        for (int i = 1; i < 5; i++) {
            values.put(String.valueOf(i), i);
        }
        model.addAttribute("values", values);
        
        //Store the uppercase field names of the Student class
        List<String> fields = studentService.getUpperFieldNames("com.annyw.springboot.bean.Student");
        model.addAttribute("fields", fields);
        
        //Get the student details by page
        List<List<Object>> students;
        students = studentService.queryStudentsByPage(Integer.parseInt(currentPage), Integer.parseInt(pageS));
        model.addAttribute("students", students);
        
        //Get the total pages needed for all the students given the page size
        int pageCount =
            studentService.getStudentListByPage(Integer.parseInt(currentPage), Integer.parseInt(pageS)).getTotalPages();
        model.addAttribute("totalCount", pageCount);
        
        //Get the user's privileges
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
            Collection<String> roles = userDetails.getRoles();
            List<Privilege> privileges;
            //If the user has admin privilege and asks to use their admin privilege, display the admin page
            if (roles.contains("ROLE_ADMIN") && admin != null) {
                privileges = userService.getUserPrivilege("ROLE_ADMIN");
                model.addAttribute("admin", true);
            }
            else {
                //User is given the user privileges
                privileges = userService.getUserPrivilege("ROLE_USER");
                model.addAttribute("admin", false);
            }
            
            //Assign corresponding privileges to the model
            for (Privilege p : privileges) {
                if (p.getName().equals("ADD_PRIVILEGE")) {
                    model.addAttribute("add", true);
                }
                if (p.getName().equals("DELETE_PRIVILEGE")) {
                    model.addAttribute("delete", true);
                }
                if (p.getName().equals("EDIT_PRIVILEGE")) {
                    model.addAttribute("edit", true);
                }
            }
        }
        return "filtered";
    }
    
    //Log out the user and display the login page
    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
    
    private String getErrorMessage(HttpServletRequest request) {
        Exception exception = (Exception)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String error;
        if (exception instanceof LockedException) {
            error = exception.getMessage();
        }
        else if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        }
        else if (exception instanceof DisabledException) {
            error = "User account is disabled! Please verify your email first!";
        }
        else {
            error = "Unknown errors";
        }
        return error;
    }
    
}
