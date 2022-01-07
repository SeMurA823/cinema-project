package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.screening.FilmScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByIdInAndEntityStatus(Collection<Long> ids, EntityStatus entityStatus);

    @Query("select p from Purchase p join p.tickets t join t.filmScreening fs " +
            "where fs = :screening and p.entityStatus = :status and fs.date > current_timestamp group by p")
    List<Purchase> findAllByScreeningAndEntityStatus(@Param("screening") FilmScreening screening,
                                                     @Param("status") EntityStatus status);

//    @Query(nativeQuery = true,
//            value = "select avg(st.count) \n" +
//                    "from sold_tickets st\n" +
//                    "join purchases p on p.id = st.purchase_id\n" +
//                    "join tickets t on p.id = t.purchase_id\n" +
//                    "join film_screenings fs on fs.id = t.film_screening_id\n" +
//                    "where p.status = 'ACTIVE' and fs.film_id = :filmId and fs.date between :startDate and :endDate")
//    Optional<Double> getAverageTicketsInPurchase(@Param("filmId") long filmId, @Param("startDate") Date start, @Param("endDate") Date end);

//    @Query(nativeQuery = true,
//    value = "select count(t.id)\n" +
//            "from tickets t\n" +
//            "         join film_screenings fs on fs.id = t.film_screening_id\n" +
//            "where t.status = 'ACTIVE' and fs.film_id = :filmId and fs.date between :startDate and :endDate")
//    Optional<Integer> getCountTickets(@Param("filmId") long filmId, @Param("startDate") Date start, @Param("endDate") Date end);
}
