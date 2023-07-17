package com.annyw.springboot.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@ComponentScan
@EnableWebSecurity
@Slf4j
public class WebSecurityConfiguration{
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;
    @Autowired
    private CustomLoginSuccessHandler loginSuccessHandler;
    
   @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(encoder());
        return provider;
    }
    
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(getDaoAuthenticationProvider())
            .build();
    }
    
    public static final String[] ENDPOINTS_WHITELIST = {
        "/static/**",
        "/",
        "/home**",
        "/submitForm",
        "/startRegister",
        "/register",
        "/registrationConfirm",
        "/resend",
        "/emailErrors"
    };
    public static final String LOGIN_URL = "/home";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error=true";
    public static final String DEFAULT_SUCCESS_URL = "/home";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    
    
    
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
    
    @Bean
    public DefaultWebSecurityExpressionHandler customExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        expressionHandler.setDefaultRolePrefix("");
        return expressionHandler;
    }
 
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf->csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                .anyRequest().authenticated())
            .formLogin(form->form
                .loginPage(LOGIN_URL)
                .loginProcessingUrl(LOGIN_URL)
                .usernameParameter(USERNAME)
                .passwordParameter(PASSWORD)
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler))
                .logout(logout -> logout
                .logoutUrl(LOGOUT_URL)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutSuccessUrl(LOGIN_URL + "?logout"))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/")
                .maximumSessions(1));
        return http.build();
    }
}
/*
        .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
            .anyRequest().authenticated())
            .formLogin(form -> form
            .loginPage(LOGIN_URL)
            .loginProcessingUrl(LOGIN_URL)
            .failureUrl(LOGIN_FAIL_URL)
            .usernameParameter(USERNAME)
            .passwordParameter(PASSWORD)
            .defaultSuccessUrl(DEFAULT_SUCCESS_URL));
        .logout(logout -> logout
                .logoutUrl(LOGOUT_URL)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutSuccessUrl(LOGIN_URL + "?logout"))
           
                
        
        .requestMatchers("/user/**")
        .hasAuthority("USER")
        .requestMatchers("/admin/**")
        .hasAuthority("ADMIN")*/
        
        
 

