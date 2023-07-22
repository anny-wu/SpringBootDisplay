package com.annyw.springboot.security;

import com.annyw.springboot.bean.User;
import com.annyw.springboot.service.AttemptsService;
import com.annyw.springboot.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Autowired
    UserService userService;
    
    @Autowired
    AttemptsService attemptsService;
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception)
        throws IOException, ServletException {
        String username = request.getParameter("username");
        
        User user = userService.getUserByUsername(username);
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (attemptsService.getAttemptByUsername(username)
                    .getAttempts() < AttemptsService.MAX_FAILED_ATTEMPTS) {
                    attemptsService.increaseFailedAttempts(user.getUsername());
                }
                else {
                    attemptsService.lock(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts."
                        + " It will be unlocked after 24 hours.");
                }
            }
            else if (!user.isAccountNonLocked()) {
                if (attemptsService.unlockWhenTimeExpired(user)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
                else {
                    exception = new LockedException("Your account is locked. Please try again later.");
                }
            }
        }
        setDefaultFailureUrl("/home?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
    
}
