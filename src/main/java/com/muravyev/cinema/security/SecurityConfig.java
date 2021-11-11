package com.muravyev.cinema.security;

import com.muravyev.cinema.entities.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/users/registration").permitAll()
                .antMatchers("/api/films/add", "/api/posters/*/add", "/api/films/*/delete", "/api/screenings/add").hasAuthority(Role.ADMIN.getAuthority())
                .antMatchers("/api/reserve/**", "/api/payment/**").hasAuthority(Role.CUSTOMER.getAuthority())
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().sessionManagement().disable()
                .userDetailsService(userDetailsService);
    }
}
