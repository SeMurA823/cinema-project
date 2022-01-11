package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientSessionEntity;
import com.muravyev.cinema.entities.session.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    List<RefreshTokenEntity> findAllByClientSessionAndEntityStatus(ClientSessionEntity clientSession, EntityStatus entityStatus);

    Optional<RefreshTokenEntity> findByTokenAndEntityStatus(String token, EntityStatus entityStatus);

    @Modifying
    @Query("update RefreshTokenEntity rt set rt.entityStatus = 'NOT_ACTIVE' where rt.clientSession.id = :cs and rt.entityStatus = 'ACTIVE'")
    void disableAllByClientSession(@Param("cs") UUID clientSession);
}
