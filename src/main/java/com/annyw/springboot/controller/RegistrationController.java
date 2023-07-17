package com.annyw.springboot.controller;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.bean.VerificationToken;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.event.OnResendTokenEvent;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;

@Controller
public class RegistrationController{
    @Autowired
    UserService userService;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    
    @GetMapping("/emailErrors")
    public String emailErrors(){return "badEmail";}
    
    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/startRegister")
    public String registerPost(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String submitForm(@Validated(ValidationSequence.class) User user, BindingResult bindingResult,
        Model model, HttpServletRequest request,  RedirectAttributes redirectAttributes) {
        try{
            if (!bindingResult.hasErrors()) {
                model.addAttribute("noErrors", true);
                User registered = userService.register(user);
                System.out.println(registered);
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                    request.getLocale(), appUrl));
                return "redirect:/home";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("error",true);
            redirectAttributes.addFlashAttribute("message", 1);
            userService.deleteUser(user);
            return "redirect:/emailErrors";
        }
        model.addAttribute("user", user);
        return "register";
    }
    
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            redirectAttributes.addFlashAttribute("message", 2);
            return "redirect:/emailErrors";
        }
        
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiredDate().getTime() - cal.getTime().getTime()) <= 0) {
            redirectAttributes.addFlashAttribute("message", 3);
            redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/emailErrors";
        }
        
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/home";
    }
    
    @PostMapping("/resend")
    public String resendToken(Model model, HttpServletRequest request,
        RedirectAttributes redirectAttributes){
        try {
            String existingToken = request.getParameter("token");
            System.out.println(existingToken);
            User user = userService.generateNewVerificationToken(existingToken);
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnResendTokenEvent(user, request.getLocale(), appUrl));
            return "redirect:/home";
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("error",true);
            redirectAttributes.addFlashAttribute("message", 1);
            return "redirect:/emailErrors";
        }
    }
    
}
