package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.screening.FilmScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByIdInAndEntityStatus(Collection<Long> ids, EntityStatus entityStatus);

    @Query("select p from Purchase p join p.tickets t join t.filmScreening fs " +
            "where fs = :screening and p.entityStatus = :status and fs.date > current_timestamp group by p")
    List<Purchase> findAllByScreeningAndEntityStatus(@Param("screening") FilmScreening screening,
                                                     @Param("status") EntityStatus status);
}
