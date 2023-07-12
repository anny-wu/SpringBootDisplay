package com.annyw.springboot.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=true");
        
        super.onAuthenticationFailure(request, response, exception);
        
        String errorMessage = "Bad Credentials";
        
        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "User account has been disabled";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "User account has expired";
        }
        
        request.getSession().setAttribute("errorMessage", errorMessage);
    }
}
