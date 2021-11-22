package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientSession;
import com.muravyev.cinema.entities.session.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByClientSessionAndEntityStatus(ClientSession clientSession, EntityStatus entityStatus);
    Optional<RefreshToken> findByTokenAndEntityStatus(String token, EntityStatus entityStatus);
    @Modifying
    @Query("update RefreshToken rt set rt.entityStatus = :status where rt.clientSession = :cs and rt.entityStatus <> :status")
    void setStatusAllByClientSession(@Param("cs") ClientSession clientSession, @Param("status") EntityStatus status);
}
