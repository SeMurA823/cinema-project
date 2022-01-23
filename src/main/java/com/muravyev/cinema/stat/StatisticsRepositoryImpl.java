package com.muravyev.cinema.stat;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.stat.statobjects.AgeLimitFilmCountStatObject;
import com.muravyev.cinema.stat.statobjects.CountryFilmCountStatObject;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {
    private final EntityManager manager;

    public StatisticsRepositoryImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<CountryFilmCountStatObject> getStatisticsCountryFilm(Date startDate, Date endDate) {
        final int MAX_RESULTS = 6;
        return manager.createQuery(
                        "SELECT new com.muravyev.cinema.stat.statobjects.CountryFilmCountStatObject(c.shortName, count(f)) " +
                                "FROM Country c left join c.films f " +
                                "where c.entityStatus = :status and f.entityStatus = :status " +
                                "and (f.localPremiere between :startDate and :endDate) " +
                                "group by c order by count(f)", CountryFilmCountStatObject.class)
                .setMaxResults(MAX_RESULTS)
                .setParameter("status", EntityStatus.ACTIVE)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public Optional<Double> getOccupancyFilm(long filmId, Date startDate, Date endDate) {
        Double avg = (Double) manager.createNativeQuery("select sum(count) / sum(hall_size) " +
                        "from hall_occupancy_stat " +
                        "where film_id = :film and (purchase_date between :startDate and :endDate)")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("film", filmId)
                .getSingleResult();
        return Optional.ofNullable(avg);
    }

    @Override
    public Optional<Double> getAverageTicketsInPurchase(long filmId, Date startDate, Date endDate) {
        BigDecimal avg = (BigDecimal) manager.createNativeQuery("select avg(count_tickets) " +
                        "from purchase_stat " +
                        "where film_id = :film and (purchase_date between :startDate and :endDate)")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("film", filmId)
                .getSingleResult();
        return Optional.ofNullable(avg).map(BigDecimal::doubleValue);
    }

    @Override
    public Optional<Long> getCountTickets(long filmId, Date startDate, Date endDate) {
        BigInteger count = (BigInteger) manager.createNativeQuery("select sum(count_tickets) " +
                        "from purchase_stat " +
                        "where film_id = :film and (purchase_date between :startDate and :endDate)")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("film", filmId)
                .getSingleResult();
        return Optional.ofNullable(count).map(BigInteger::longValue);
    }

    @Override
    public List<AgeLimitFilmCountStatObject> getStatisticsLimitsFilm(Date startDate, Date endDate) {
        return manager.createQuery("select " +
                                "new com.muravyev.cinema.stat.statobjects.AgeLimitFilmCountStatObject(al.name, count(f)) " +
                                "from AgeLimit al " +
                                "join al.films f " +
                                "where f.entityStatus = 'ACTIVE' and al.entityStatus = 'ACTIVE' " +
                                "and (f.localPremiere between :startDate and :endDate) " +
                                "group by al " +
                                "order by al.startAge",
                        AgeLimitFilmCountStatObject.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}
