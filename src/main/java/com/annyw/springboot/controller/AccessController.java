package com.annyw.springboot.controller;

import com.annyw.springboot.bean.Student;
import com.annyw.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//Handle actions that can be performed by the user
@Controller
public class AccessController {
    @Autowired
    StudentService studentService;
    
    private static final String CLASSNAME = "com.annyw.springboot.bean.Student";
    
    //Get the page for user to add a student
    @GetMapping("/addStudent")
    public String addStudent(Model model)
        throws ClassNotFoundException {
        //Create a student object for binding
        model.addAttribute("student", new Student());
        //Store the lowercase field names in the model
        model.addAttribute("lowercase", studentService.getlowerFieldNames(CLASSNAME));
        //Store the uppercase field names in the model
        model.addAttribute("uppercase", studentService.getUpperFieldNames(CLASSNAME));
        return "add";
    }
    
    //Handle the add student request
    @PostMapping("/addStudent")
    public String handleAdd(Student student, BindingResult bindingResult, Model model)
        throws ClassNotFoundException {
        if (!bindingResult.hasErrors()) {
            //If student already exists, display error message
            if (studentService.studentExists(student.getUsername()) != null) {
                model.addAttribute("errorMsg", "student already exists!");
            }
            else {
                //Add students to database
                studentService.addStudent(student);
                return "redirect:/display?admin";
            }
        }
        
        //Create another student object for binding
        model.addAttribute("student", new Student());
        //Store the lowercase field names in the model
        model.addAttribute("lowercase", studentService.getlowerFieldNames(CLASSNAME));
        //Store the uppercase field names in the model
        model.addAttribute("uppercase", studentService.getUpperFieldNames(CLASSNAME));
        return "add";
    }
    
    //Get the page for the user to delete a student
    @GetMapping("/deleteStudent")
    public String deleteStudent(Model model)
        throws ClassNotFoundException, IllegalAccessException {
        //Get the existing students from database
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        model.addAttribute("deleted", students.get(0).get(0));
        return "delete";
    }
    
    //Handle the delete student request
    @PostMapping("/deleteStudent")
    public String handleDelete(@RequestParam("id") Long id) {
        //If a student is selected, delete the student from database
        if (id != null) {
            studentService.deleteStudent(id);
            return "redirect:/display?admin";
        }
        return "delete";
    }
    
    //Get the page for the user to edit a student
    @GetMapping("/editStudent")
    public String editStudent(Model model,
        @RequestParam(value = "admin", required = false) String admin)
        throws ClassNotFoundException,
        IllegalAccessException {
        //Get the existing students from database
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        
        //Create a student object for binding
        model.addAttribute("student", new Student());
        //Store the lowercase field names in the model
        model.addAttribute("lowercase", studentService.getlowerFieldNames(CLASSNAME));
        //Store the uppercase field names in the model
        model.addAttribute("uppercase", studentService.getUpperFieldNames(CLASSNAME));
        
        //Check if the user comes from admin access
        if (admin != null) {
            model.addAttribute("admin", true);
        }
        else {
            model.addAttribute("admin", false);
        }
        return "edit";
    }
    
    //Handle the edit student request
    @PostMapping("/editStudent")
    public String handleEdit(@RequestParam("id") int id,
        @RequestParam(value = "admin", required = false) String admin, Student student,
        Model model, BindingResult bindingResult)
        throws ClassNotFoundException, IllegalAccessException {
        String username = studentService.convertUsername(student.getUsername());
        Student found = studentService.studentExists(username);
        if (!bindingResult.hasErrors()) {
            if (found != null  && (!found.getUsername().equals(username))) {
                /* If the user is attempting to change the student's name to an existing student's name that is not
                themselves, display error message */
                model.addAttribute("errorMsg", "student already exists!");
                model.addAttribute("selected", id);
                
                //Check if the user comes from admin access
                if (admin != null) {
                    model.addAttribute("admin", true);
                }
                else {
                    model.addAttribute("admin", false);
                }
            }
            else {
                //Edit the student
                studentService.editStudent(student);
                //Check if the user comes from admin access
                if (admin != null) {
                    return "redirect:/display?admin";
                }
                else {
                    return "redirect:/display";
                }
            }
        }
        
        //Get the existing students from database if there is an error
        List<List<Object>> students = studentService.getStudentList();
        model.addAttribute("students", students);
        
        //Create another student object for binding
        model.addAttribute("student", new Student());
        //Store the lowercase field names in the model
        model.addAttribute("lowercase", studentService.getlowerFieldNames(CLASSNAME));
        //Store the uppercase field names in the model
        model.addAttribute("uppercase", studentService.getUpperFieldNames(CLASSNAME));
        return "edit";
    }
}
