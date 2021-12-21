package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.roles.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
