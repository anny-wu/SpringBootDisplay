package com.annyw.springboot.listener;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.event.OnResendTokenEvent;
import com.annyw.springboot.repo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ResendListener implements
    ApplicationListener<OnResendTokenEvent> {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TokenRepository tokenRepository;
    
    @Override
    public void onApplicationEvent(OnResendTokenEvent event) {
        this. resendToken(event);
    }
    
    private void resendToken(OnResendTokenEvent event) {
        //Get the user that requests the token to be resend
        User user = event.getUser();
        //Get the new token of the given user
        String token = tokenRepository.findByUser(user).getToken();
        String recipientAddress = user.getEmail();
        String subject = "Resend Registration Token";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        //Set up the email object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("anny_wu@qq.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Confirm Your Email Account Here\r\n" + "http://localhost:8080" + confirmationUrl);
        //Send the email
        mailSender.send(email);
    }
}
