package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findAllByPurchaseId(Long purchaseId, Pageable pageable);

//    @Modifying
//    @Query("update Ticket t " +
//            "set t.entityStatus = :status " +
//            "where t.purchase.user = :user and t.id = :id " +
//            "and t.entityStatus = 'ACTIVE'")
//    int updateStatusByIdAndUserAndEntityStatus(@Param("id") long id,
//                                               @Param("user") User user,
//                                               @Param("status") EntityStatus status);

    Optional<Ticket> findByIdAndPurchaseUserAndEntityStatus(Long id, User purchaseUser, EntityStatus entityStatus);

    List<Ticket> findAllByIdInAndEntityStatus(Collection<Long> id,
                                              EntityStatus entityStatus);

//    @Modifying
//    @Query("update Ticket t " +
//            "set t.entityStatus = :status " +
//            "where t.id in (:ids) " +
//            "and t.entityStatus = 'ACTIVE'")
//    int updateStatusAllByIds(@Param("ids") Collection<Long> id,
//                             @Param("status") EntityStatus status);

    Page<Ticket> findAllByPurchaseUserAndFilmScreeningDateAfterAndEntityStatus(User purchaseUser,
                                                                               Date filmScreeningDate,
                                                                               EntityStatus entityStatus,
                                                                               Pageable pageable);

    List<Ticket> findAllByPurchaseAndEntityStatus(Purchase purchase, EntityStatus entityStatus);

    boolean existsBySeatAndFilmScreeningAndEntityStatus(Seat seat, FilmScreening filmScreening, EntityStatus entityStatus);

}
