package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.RoleRepository;
import com.muravyev.cinema.repo.UserRepository;
import com.muravyev.cinema.services.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserRole setRole(User user, Role role) {
        return setRole(user.getId(), role);
    }

    @Override
    public UserRole setRole(long userId, Role role) {
        Optional<UserRole> candidateRole = roleRepository.findByUserIdAndRoleAndEntityStatus(userId, role,
                EntityStatus.ACTIVE);
        if (candidateRole.isEmpty()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(EntityNotFoundException::new);
            UserRole userRole = new UserRole(role, user);
            return roleRepository.save(userRole);
        }
        return candidateRole.get();
    }

    @Override
    public List<UserRole> getActiveRoles(long userId) {
        return roleRepository.findAllByUserIdAndEntityStatus(userId, EntityStatus.ACTIVE);
    }

    @Override
    public void setRolesStatus(Collection<Long> ids, EntityStatus status) {
        List<UserRole> roles = roleRepository.findAllById(ids);
        roles.forEach(x -> x.setEntityStatus(status));
        roleRepository.saveAll(roles);
    }
}
