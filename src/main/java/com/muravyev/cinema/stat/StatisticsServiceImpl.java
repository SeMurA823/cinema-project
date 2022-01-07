package com.muravyev.cinema.stat;

import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.stat.statobjects.AgeLimitFilmCountStatObject;
import com.muravyev.cinema.stat.statobjects.CountryFilmCountStatObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public double getOccupancyScreenings(long filmId, Date start, Date end) {
        return statisticsRepository.getOccupancyScreeningId(filmId, start, end).orElse(0.);
    }

    @Override
    public double getAverageTicketsInPurchase(long filmId, Date start, Date end) {
        return statisticsRepository.getAverageTicketsInPurchase(filmId, start, end).orElse(0.);
    }

    @Override
    public long getCountTickets(long filmId, Date start, Date end) {
        return statisticsRepository.getCountTickets(filmId, start, end).orElse(0L);
    }

    @Override
    public List<CountryFilmCountStatObject> getCountryCountStatistics(Date start, Date end) {
        return statisticsRepository.getStatisticsCountryFilm(start, end);
    }

    @Override
    public List<AgeLimitFilmCountStatObject> getAgeLimitCountStatistics(Date start, Date end) {
        return statisticsRepository.getStatisticsLimitsFilm();
    }
}
