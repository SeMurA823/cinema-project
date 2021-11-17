package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.users.RefreshToken;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenAndEntityStatus(String token, EntityStatus entityStatus);
    Optional<RefreshToken>  findByUser(User user);
}
