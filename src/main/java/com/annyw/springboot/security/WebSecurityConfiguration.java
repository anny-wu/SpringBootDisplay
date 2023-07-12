package com.annyw.springboot.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;

@Configuration
@ComponentScan
@EnableWebSecurity
public class WebSecurityConfiguration{
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    public static final String[] ENDPOINTS_WHITELIST = {
        "/static/**",
        "/",
        "/home",
        "/register",
        "/registrationConfirm",
        "/emailErrors"
    };
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String LOGIN_FAIL_URL = LOGIN_URL + "?error";
    public static final String DEFAULT_SUCCESS_URL = "/test.html";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    /*
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .csrf(csrf->csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                .requestMatchers("/static/**","/about").permitAll()
                .requestMatchers(ENDPOINTS_WHITELIST).permitAll());
                    /*.requestMatchers("/user/**")
                    .hasAuthority("USER")
                    .requestMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                    .anyRequest().authenticated())
                .formLogin(form -> form
                    .loginPage(LOGIN_URL)
                    .loginProcessingUrl(LOGIN_URL)
                    .failureUrl(LOGIN_FAIL_URL)
                    .usernameParameter(USERNAME)
                    .passwordParameter(PASSWORD)
                    .defaultSuccessUrl(DEFAULT_SUCCESS_URL))
                .logout(logout -> logout
                    .logoutUrl(LOGOUT_URL)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl(LOGIN_URL + "?logout"))
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .invalidSessionUrl("/invalidSession")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true))
                ;*/
        return http.build();
    }
   
}

