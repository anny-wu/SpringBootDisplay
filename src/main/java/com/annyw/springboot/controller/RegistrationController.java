package com.annyw.springboot.controller;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.bean.VerificationToken;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.service.UserService;
import com.annyw.springboot.validator.ValidationSequence;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

@Controller
public class RegistrationController{
    @Autowired
    UserService userService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }
    
    @GetMapping("/home")
    public String loginGet(){return "login";}
    
    @PostMapping("/home")
    public String loginPost(){return "login";}
    
    @GetMapping("/emailErrors")
    public String emailErrors(){return "badEmail";}
    
    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerPost(@Validated(ValidationSequence.class) User user, BindingResult bindingResult,
        Model model, HttpServletRequest request,  RedirectAttributes redirectAttributes) {
        try{
            if (!bindingResult.hasErrors()) {
                model.addAttribute("noErrors", true);
                User registered = userService.register(user);
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                    request.getLocale(), appUrl));
                return "redirect:/home";
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error",true);
            redirectAttributes.addFlashAttribute("message", 1);
            return "redirect:/emailErrors";
        }
        model.addAttribute("user", user);
        return "register";
    }
    
    @GetMapping("/registrationConfirm")
    public String confirmRegistration
        (WebRequest request, Model model, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        System.out.println("Enter method");
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            redirectAttributes.addFlashAttribute("message", 2);
            return "redirect:/emailErrors";
        }
        
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        System.out.println("Current time: " + cal.getTime().getTime());
        System.out.println("Expired time: " + verificationToken.getExpiredDate().getTime());
        if ((verificationToken.getExpiredDate().getTime() - cal.getTime().getTime()) <= 0) {
            redirectAttributes.addFlashAttribute("message", 3);
            return "redirect:/emailErrors";
        }
        
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        System.out.println("Saved user: " + user);
        return "redirect:/home";
    }
    
}
