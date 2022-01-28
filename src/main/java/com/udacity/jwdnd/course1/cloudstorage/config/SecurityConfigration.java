package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfigration extends WebSecurityConfigurerAdapter {

    private AuthenticationService authenticationService;

    public SecurityConfigration(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


// This is as an example configure from Udacity Course
    @Override
    protected void configure(AuthenticationManagerBuilder authentication) {
        authentication.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       // "The first method chain permits all requests to the signup page"
        http.authorizeRequests()
                .antMatchers("/signup", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated();

       // "Then permits all users to access login page"
        http.formLogin()
                .loginPage("/login")
                .permitAll();

        //" Then  redirects successful logins to the home Page"

        http.formLogin()
                .defaultSuccessUrl("/home", true);
    }
}
