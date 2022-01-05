package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.CountStatisticsObject;
import com.muravyev.cinema.dto.StatisticsObject;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.entities.film.Country;

import java.util.Date;
import java.util.List;

public interface StatisticsService {

    StatisticsObject<Double> getOccupancyScreenings(long filmId, Date start, Date end);

    StatisticsObject<Double> getAverageTicketsInPurchase(long filmId, Date start, Date end);

    StatisticsObject<Integer> getCountTickets(long filmId, Date start, Date end);

    List<CountStatisticsObject<Country>> getCountryCountStatistics();

    List<CountStatisticsObject<AgeLimit>> getAgeLimitCountStatistics();

    StatisticsObject<Double> getCountTicketByFilmAndScreeningPeriod(long filmId,
                                                                    Date start,
                                                                    Date end,
                                                                    int isodow,
                                                                    int hour);

    List<StatisticsObject<Double>> getCountTicketByFilmAndScreeningInDate(long filmId,
                                                                          Date start,
                                                                          Date end,
                                                                          int isodow);

    List<List<StatisticsObject<Double>>> getCountTicketByFilmAndScreeningInWeek(long filmId,
                                                                                Date start,
                                                                                Date end);

    List<List<StatisticsObject<Double>>> getOccupancyInPreviousWeek(long filmId);
}
