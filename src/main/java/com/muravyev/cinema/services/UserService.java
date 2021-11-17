package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registration(RegistrationDto registrationForm);

    User login(String username, String password);

    User login(LoginDto loginDto);
}
