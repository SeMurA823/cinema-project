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
        final int top = 6;
        List<CountryFilmCountStatObject> list = manager.createQuery(
                        "SELECT new com.muravyev.cinema.stat.statobjects.CountryFilmCountStatObject(c.shortName, count(f)) FROM Country c left join c.films f " +
                                "where c.entityStatus = :status and f.entityStatus = :status " +
                                "and (f.localPremiere between :startDate and :endDate) " +
                                "group by c order by count(f)", CountryFilmCountStatObject.class)
                .setMaxResults(6)
                .setParameter("status", EntityStatus.ACTIVE)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
//        List<CountryFilmCountStatObject> topCountries = list.stream()
//                .limit(top)
//                .collect(Collectors.toList());
//        long otherCount = list.stream()
//                .skip(top)
//                .mapToLong(CountryFilmCountStatObject::getCount)
//                .sum();
//        topCountries.add(new CountryFilmCountStatObject("Другое", otherCount));
        return list;
    }

    @Override
    public Optional<Double> getOccupancyScreeningId(long filmId, Date startDate, Date endDate) {
        Double avg = (Double) manager.createNativeQuery("select case when avg(ocf.occupancy)<>0 then avg(ocf.occupancy) else 0 end" +
                        "                    from occupancy_film ocf" +
                        "                            join films f on f.id = ocf.film_id" +
                        "                            join halls h on h.id = ocf.hall_id" +
                        "                            join seats s on h.id = s.hall_id" +
                        "                            left outer join tickets t on ocf.id = t.film_screening_id" +
                        "                               and s.id = t.seat_id and (t.status = 'ACTIVE')" +
                        "                    where f.id = :film and (ocf.date between :startDate and :endDate)")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("film", filmId)
                .getSingleResult();
        return Optional.of(avg);
    }

    @Override
    public Optional<Double> getAverageTicketsInPurchase(long filmId, Date startDate, Date endDate) {
        BigDecimal avg = (BigDecimal) manager.createNativeQuery("select avg(st.count) \n" +
                                "from sold_tickets st\n" +
                                "join purchases p on p.id = st.purchase_id\n" +
                                "join tickets t on p.id = t.purchase_id\n" +
                                "join film_screenings fs on fs.id = t.film_screening_id\n" +
                                "where p.status = 'ACTIVE' and fs.film_id = :film and fs.date between :startDate and :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("film", filmId)
                .getSingleResult();
        return Optional.of(avg).map(BigDecimal::doubleValue);
    }

    @Override
    public Optional<Long> getCountTickets(long filmId, Date startDate, Date endDate) {
        BigInteger count = (BigInteger) manager.createNativeQuery("select count(t.id)\n" +
                                "from tickets t\n" +
                                "         join film_screenings fs on fs.id = t.film_screening_id\n" +
                                "where t.status = 'ACTIVE' and fs.film_id = :film and fs.date between :startDate and :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("film", filmId)
                .getSingleResult();
        return Optional.of(count).map(BigInteger::longValue);
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
