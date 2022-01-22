package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.UserRepository;
import com.muravyev.cinema.services.AdminService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User appointAdmin(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.ADMIN);
        user.getUserRoles().add(userRole);
        return userRepository.save(user);
    }

    @Override
    public User demoteAdmin(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
        user.getUserRoles().stream()
                .filter(x -> x.getRole() == Role.ADMIN)
                .forEach(x -> x.setEntityStatus(EntityStatus.NOT_ACTIVE));
        return userRepository.save(user);
    }


}
