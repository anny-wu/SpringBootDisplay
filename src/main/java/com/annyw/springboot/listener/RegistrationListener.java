package com.annyw.springboot.listener;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.service.UserService;
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
    private UserService service;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
    
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
            = event.getAppUrl() + "/registrationConfirm?token=" + token;
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setFrom("anny_wu@qq.com");
        email.setSubject(subject);
        email.setText("Confirm Your Email Account Here\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
