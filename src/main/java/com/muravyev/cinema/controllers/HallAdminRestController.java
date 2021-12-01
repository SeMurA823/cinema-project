package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.services.HallService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/hall")
public class HallAdminRestController {
    private final HallService hallService;

    public HallAdminRestController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createHall(String name) {
        Hall hall = hallService.setHall(name);
        return ResponseEntity.ok(hall);
    }

    @PostMapping(value = "{hall}/seats", params = {"row", "num"})
    public ResponseEntity<?> addSeat(@PathVariable("hall") long hallId,
                                     @RequestParam("row") int row,
                                     @RequestParam("num") int num) {
        Seat seat = hallService.addSeat(hallId, num, row);
        return ResponseEntity.ok(seat);
    }

    @PostMapping(value = "{hall}/seats", params = {"row", "size"})
    public ResponseEntity<?> addSeats(@PathVariable("hall") long hallId,
                                      @RequestParam("row") int row,
                                      @RequestParam("size") int size) {
        List<Seat> seats = hallService.addSeats(hallId, row, size);
        return ResponseEntity.ok(seats);
    }
}
