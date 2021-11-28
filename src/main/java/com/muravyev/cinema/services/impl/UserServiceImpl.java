package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.dto.UserInfoDto;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.entities.users.UserStatus;
import com.muravyev.cinema.repo.UserRepository;
import com.muravyev.cinema.services.CustomerService;
import com.muravyev.cinema.services.RoleService;
import com.muravyev.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private CustomerService customerService;

    private PasswordEncoder passwordEncoder;

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
    }

    @Override
    @Transactional
    public User registration(RegistrationDto registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setBirthDate(registrationForm.getBirthDate());
        user.setPatronymic(registrationForm.getPatronymic());
        user.setGender(registrationForm.getGender());
        user = userRepository.save(user);
        user.setUserRoles(Set.of(roleService.setRole(user, Role.CUSTOMER)));
        customerService.registration(registrationForm, user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new AuthenticationServiceException("Password is illegal");
        return user;
    }

    @Override
    public User login(LoginDto loginDto) {
        return login(loginDto.getUsername(), loginDto.getPassword());
    }

    @Override
    public User editPassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public User editUserInfo(UserInfoDto userInfo, User user) {
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());
        user.setPatronymic(userInfo.getPatronymic());
        user.setBirthDate(userInfo.getBirthDate());
        return userRepository.save(user);
    }
}
