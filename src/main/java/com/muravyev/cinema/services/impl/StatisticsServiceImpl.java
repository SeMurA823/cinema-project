package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.CountStatisticsObject;
import com.muravyev.cinema.dto.StatisticsObject;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.repo.*;
import com.muravyev.cinema.services.StatisticsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final FilmScreeningSeatRepository screeningSeatRepository;
    private final FilmScreeningRepository screeningRepository;
    private final PurchaseRepository purchaseRepository;
    private final CountryRepository countryRepository;
    private final FilmRepository filmRepository;
    private final AgeLimitRepository ageLimitRepository;

    public StatisticsServiceImpl(FilmScreeningSeatRepository screeningSeatRepository,
                                 FilmScreeningRepository screeningRepository,
                                 PurchaseRepository purchaseRepository,
                                 CountryRepository countryRepository,
                                 FilmRepository filmRepository,
                                 AgeLimitRepository ageLimitRepository) {
        this.screeningSeatRepository = screeningSeatRepository;
        this.screeningRepository = screeningRepository;
        this.purchaseRepository = purchaseRepository;
        this.countryRepository = countryRepository;
        this.filmRepository = filmRepository;
        this.ageLimitRepository = ageLimitRepository;
    }

    @Override
    public StatisticsObject<Double> getOccupancyScreenings(long filmId, Date start, Date end) {
        double occupancy = screeningSeatRepository.getOccupancyScreeningId(filmId, start, end).orElse(0.);
        return new StatisticsObject<>(occupancy, start, end);
    }

    @Override
    public StatisticsObject<Double> getAverageTicketsInPurchase(long filmId, Date start, Date end) {
        double average = purchaseRepository.getAverageTicketsInPurchase(filmId, start, end).orElse(0.);
        return new StatisticsObject<>(average, start, end);
    }

    @Override
    public StatisticsObject<Integer> getCountTickets(long filmId, Date start, Date end) {
        int average = purchaseRepository.getCountTickets(filmId, start, end).orElse(0);
        return new StatisticsObject<>(average, start, end);
    }

    @Override
    public List<CountStatisticsObject<Country>> getCountryCountStatistics() {
        List<Country> countries = countryRepository.findAll(Sort.by("shortName").ascending());
        return countries.stream()
                .parallel()
                .map(x -> new CountStatisticsObject<>(x, filmRepository.countByCountry(x)))
                .collect(Collectors.toList());
    }

    @Override
    public List<CountStatisticsObject<AgeLimit>> getAgeLimitCountStatistics() {
        List<AgeLimit> limits = ageLimitRepository.findAll(Sort.by("startAge").ascending());
        return limits.stream()
                .parallel()
                .map(x -> new CountStatisticsObject<>(x, filmRepository.countByAgeLimit(x)))
                .collect(Collectors.toList());
    }

    @Override
    public StatisticsObject<Double> getCountTicketByFilmAndScreeningPeriod(long filmId,
                                                                           Date start,
                                                                           Date end,
                                                                           int isodow,
                                                                           int hour) {
        return new StatisticsObject<>(
                screeningSeatRepository.getOccupancyByFilmAndScreeningPeriod(filmId,
                        start,
                        end,
                        isodow,
                        hour).orElse(0.),
                start,
                end
        );
    }

    @Override
    public List<StatisticsObject<Double>> getCountTicketByFilmAndScreeningInDate(long filmId,
                                                                                 Date start,
                                                                                 Date end,
                                                                                 int isodow) {
        return IntStream.range(0,24)
                .parallel()
                .mapToObj(x->getCountTicketByFilmAndScreeningPeriod(filmId,
                        start,
                        end,
                        isodow,
                        x))
                .collect(Collectors.toList());
    }

    @Override
    public List<List<StatisticsObject<Double>>> getCountTicketByFilmAndScreeningInWeek(long filmId,
                                                                                       Date start,
                                                                                       Date end) {
        if (end.getTime() - start.getTime() < 7 * 24 * 60 * 60 * 1000)
            throw new IllegalArgumentException("Date period is illegal");
        List<List<StatisticsObject<Double>>> list = IntStream.rangeClosed(0, 6)
                .parallel()
                .mapToObj(x -> getCountTicketByFilmAndScreeningInDate(filmId,
                        start,
                        end,
                        x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<List<StatisticsObject<Double>>> getOccupancyInPreviousWeek(long filmId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.add(Calendar.DATE, -7);
        Date startWeek = calendar.getTime();
        calendar.add(Calendar.DATE, 7);
        Date endWeek = calendar.getTime();
        return getCountTicketByFilmAndScreeningInWeek(filmId, startWeek, endWeek);
    }
}
