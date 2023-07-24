package com.annyw.springboot.listener;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
    ApplicationListener<OnRegistrationCompleteEvent> {
    
    @Autowired
    private TokenService service;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
    
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        //Get the user that is currently registering
        User user = event.getUser();
        //Generate a random token
        String token = UUID.randomUUID().toString();
        //Assign the token to the given user
        service.createVerificationToken(user, token);
        
        String senderAddress = "anny_wu@qq.com";
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        
        //Set the email object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(senderAddress);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Confirm Your Email Account Here\r\n" + "http://localhost:8080" + confirmationUrl);
       
        //Send the email
        mailSender.send(email);
    }
}
