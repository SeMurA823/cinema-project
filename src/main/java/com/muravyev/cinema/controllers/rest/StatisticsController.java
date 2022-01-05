package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.StatisticsObject;
import com.muravyev.cinema.services.StatisticsService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/stat")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/averageticketcount", params = {"film", "start", "end"})
    public ResponseEntity<?> averageTicketCount(@RequestParam("film") long filmId,
                                                @RequestParam("start") Date start,
                                                @RequestParam("end") Date end) {
        return ResponseEntity.ok(statisticsService.getAverageTicketsInPurchase(filmId, start, end) );
    }

    @GetMapping(value = "/soldtickets", params = {"lastmonth"})
    public ResponseEntity<?> soldTickets() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/occupancyfilm", params = {"film", "start", "end"})
    public ResponseEntity<?> occupancyScreenings(@RequestParam("film") long filmId,
                                                 @RequestParam("start") Date start,
                                                 @RequestParam("end") Date end) {
        StatisticsObject<Double> statistics = statisticsService.getOccupancyScreenings(filmId, start, end);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping(value = "/amounttickets", params = {"film", "start", "end"})
    public ResponseEntity<?> countTickets(@RequestParam("film") long filmId,
                                                 @RequestParam("start") Date start,
                                                 @RequestParam("end") Date end) {
        StatisticsObject<Integer> statistics = statisticsService.getCountTickets(filmId, start, end);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/filmcountries")
    public ResponseEntity<?> countriesFilms() {
        return ResponseEntity.ok(statisticsService.getCountryCountStatistics());
    }

    @GetMapping("/filmlimits")
    public ResponseEntity<?> limitsFilms() {
        return ResponseEntity.ok(statisticsService.getAgeLimitCountStatistics());
    }

    @GetMapping(value = "/occupancyweek", params = {"film"})
    public ResponseEntity<?> occupancyWeek(@RequestParam("film") long filmId) {
        return ResponseEntity.ok(statisticsService.getOccupancyInPreviousWeek(filmId));
    }

}
