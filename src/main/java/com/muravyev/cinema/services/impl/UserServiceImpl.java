package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.entities.users.UserStatus;
import com.muravyev.cinema.repo.UserRepository;
import com.muravyev.cinema.services.CustomerService;
import com.muravyev.cinema.services.RoleService;
import com.muravyev.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User registration(RegistrationDto registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        user = userRepository.save(user);
        customerService.registration(registrationForm, user);
        user.setUserRoles(Set.of(roleService.setRole(user, Role.CUSTOMER)));
        return user;
    }
}
