package com.annyw.springboot.listener;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.event.OnRegistrationCompleteEvent;
import com.annyw.springboot.event.OnResendTokenEvent;
import com.annyw.springboot.repo.TokenRepository;
import com.annyw.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ResendListener implements
    ApplicationListener<OnResendTokenEvent> {
    
    @Autowired
    private UserService service;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Override
    public void onApplicationEvent(OnResendTokenEvent event) {
        this. resendToken(event);
    }
    
    private void resendToken(OnResendTokenEvent event) {
        User user = event.getUser();
        String token = tokenRepository.findByUser(user).getToken();
        String recipientAddress = user.getEmail();
        String subject = "Resend Registration Token";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("anny_wu@qq.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Confirm Your Email Account Here\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}
