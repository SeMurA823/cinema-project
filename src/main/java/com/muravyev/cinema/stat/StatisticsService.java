package com.muravyev.cinema.stat;

import com.muravyev.cinema.stat.statobjects.AgeLimitFilmCountStatObject;
import com.muravyev.cinema.stat.statobjects.CountryFilmCountStatObject;

import java.util.Date;
import java.util.List;

public interface StatisticsService {

    double getOccupancyScreenings(long filmId, Date start, Date end);

    double getAverageTicketsInPurchase(long filmId, Date start, Date end);

    long getCountTickets(long filmId, Date start, Date end);

    List<CountryFilmCountStatObject> getCountryCountStatistics(Date start, Date end);

    List<AgeLimitFilmCountStatObject> getAgeLimitCountStatistics(Date start, Date end);
}
