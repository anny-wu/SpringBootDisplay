package com.annyw.springboot.event;

import com.annyw.springboot.bean.User;
import org.springframework.context.ApplicationEvent;

//Event fires when the system attempts to resend the verification token through email
public class OnResendTokenEvent extends ApplicationEvent {
    private String appUrl;
    private User user;
    
    public OnResendTokenEvent(User user, String appUrl) {
        super(user);
        
        this.user = user;
        this.appUrl = appUrl;
    }
    
    public String getAppUrl() {
        return appUrl;
    }
    
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}
