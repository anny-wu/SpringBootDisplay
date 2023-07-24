package com.annyw.springboot.controller;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.bean.VerificationToken;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.event.OnResendTokenEvent;
import com.annyw.springboot.service.TokenService;
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
public class RegistrationController {
    @Autowired
    UserService userService;
    
    @Autowired
    TokenService tokenService;
    
    @Autowired
    ApplicationEventPublisher eventPublisher;
    
    //Show page if there is an email error
    @GetMapping("/emailErrors")
    public String emailErrors() {
        return "badEmail";
    }
    
    //User goes to the sign-up page
    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    //User arrives at the sign-up page
    @PostMapping("/startRegister")
    public String registerPost(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    //User attempts to finish registering
    @PostMapping("/register")
    public String submitForm(@Validated(ValidationSequence.class) User user, BindingResult bindingResult,
        Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            //If the form fields successfully bind to a user
            if (!bindingResult.hasErrors()) {
                model.addAttribute("noErrors", true);
                //Send an email with the verification token to enable the user account
                String appUrl = request.getContextPath();
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,appUrl));
                //Register the user
                userService.register(user);
                //Redirect the user to the home page
                return "redirect:/home";
            }
        }
        catch (RuntimeException e) {
            //If an email fails to send
            e.printStackTrace();
            //Assign the error message of "Email could not be sent"
            model.addAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", 1);
            //Redirect the user to the email error page to try to sign up again
            return "redirect:/emailErrors";
        }
        /*
          If the form fields do not successfully bind to a user, returns the user to the sign-up page
          with fields that were successful
        */
        model.addAttribute("user", user);
        return "register";
    }
    
    //User clicks on the verification link in their email
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        //Get the verification token object stored in the database for the given token
        VerificationToken verificationToken = tokenService.getVerificationToken(token);
        if (verificationToken == null) {
            //If the token can not be found in the database
            redirectAttributes.addFlashAttribute("message", 2);
            return "redirect:/emailErrors";
        }
        
        //Get the user corresponds to the token
        User user = verificationToken.getUser();
        //Check if the token is valid or prompts the user to resend the token otherwise
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiredDate().getTime() - cal.getTime().getTime()) <= 0) {
            redirectAttributes.addFlashAttribute("message", 3);
            redirectAttributes.addFlashAttribute("token", token);
            return "redirect:/emailErrors";
        }
        
        //Enable user's account
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/home";
    }
    
    //User clicks on resend verification token link
    @PostMapping("/resend")
    public String resendToken(@RequestParam("token") String token, Model model, HttpServletRequest request,
        RedirectAttributes redirectAttributes) {
        try {
            //Generate the new verification token for the user
            User user = tokenService.generateNewVerificationToken(token);
            //Resend the email
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnResendTokenEvent(user, appUrl));
            return "redirect:/home";
        }
        catch (RuntimeException e) {
            //If an email fails to send
            e.printStackTrace();
            //Assign the error message of "Email could not be sent"
            model.addAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", 1);
            //Redirect the user to the email error page to try to sign up again
            return "redirect:/emailErrors";
        }
    }
}
