package com.muravyev.cinema.stat;

import org.springframework.format.annotation.DateTimeFormat;
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
                                                @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return ResponseEntity.ok(statisticsService.getAverageTicketsInPurchase(filmId, start, end));
    }

    @GetMapping(value = "/soldtickets", params = {"lastmonth"})
    public ResponseEntity<?> soldTickets() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/occupancyfilm", params = {"film", "start", "end"})
    public ResponseEntity<?> occupancyScreenings(@RequestParam("film") long filmId,
                                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return ResponseEntity.ok(statisticsService.getOccupancyScreenings(filmId, start, end));
    }

    @GetMapping(value = "/amounttickets", params = {"film", "start", "end"})
    public ResponseEntity<?> countTickets(@RequestParam("film") long filmId,
                                          @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                          @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return ResponseEntity.ok(statisticsService.getCountTickets(filmId, start, end));
    }

    @GetMapping(value = "/filmcountries", params = {"start", "end"})
    public ResponseEntity<?> countriesFilms(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        return ResponseEntity.ok(statisticsService.getCountryCountStatistics(startDate, endDate));
    }

    @GetMapping(value = "/filmlimits", params = {"start", "end"})
    public ResponseEntity<?> limitsFilms(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
                                         @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate) {
        return ResponseEntity.ok(statisticsService.getAgeLimitCountStatistics(startDate, endDate));
    }

}
