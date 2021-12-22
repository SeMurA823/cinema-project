package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.dto.UserInfoDto;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.entities.users.UserStatus;
import com.muravyev.cinema.repo.UserRepository;
import com.muravyev.cinema.repo.UserRoleRepository;
import com.muravyev.cinema.services.RoleService;
import com.muravyev.cinema.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void setUserStatus(UserStatus status, Collection<Long> ids) {
        List<User> users = userRepository.findAllById(ids);
        users.forEach(x -> x.setUserStatus(status));
        userRepository.saveAll(users);
    }
}
