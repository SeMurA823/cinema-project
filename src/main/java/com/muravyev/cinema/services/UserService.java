package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.dto.UserInfoDto;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registration(RegistrationDto registrationForm);

    User login(String username, String password);

    User login(LoginDto loginDto);

    User editPassword(String newPassword, User user);

    User editUserInfo(UserInfoDto userInfo, User user);

    Page<User> getAllUsers(Pageable pageable);
}
