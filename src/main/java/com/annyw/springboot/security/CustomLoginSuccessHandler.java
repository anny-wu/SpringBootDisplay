package com.annyw.springboot.security;

import java.io.IOException;

import com.annyw.springboot.bean.CustomUserDetails;
import com.annyw.springboot.bean.User;
import com.annyw.springboot.service.AttemptsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    @Autowired
    private AttemptsService attemptsService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication)
        throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        User user = userDetails.getUser();
        if (attemptsService.getAttemptByUsername(user.getUsername()).getAttempts() > 0) {
            attemptsService.resetFailedAttempts(user.getUsername());
        }
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
