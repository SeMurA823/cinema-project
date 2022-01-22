package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.roles.Role;
import com.muravyev.cinema.entities.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findAllByUserIdAndEntityStatus(Long userId, EntityStatus entityStatus);

    Optional<UserRole> findByUserIdAndRoleAndEntityStatus(Long userId, Role role, EntityStatus entityStatus);

    boolean existsByUserIdAndRoleAndEntityStatus(Long userId, Role role, EntityStatus entityStatus);

}
