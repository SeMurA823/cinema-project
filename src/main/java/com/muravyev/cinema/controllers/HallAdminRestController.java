package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.HallDto;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.services.HallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/halls")
public class HallAdminRestController {
    private final HallService hallService;

    public HallAdminRestController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public ResponseEntity<?> getAllHalls(@PageableDefault Pageable pageable) {
        Page<Hall> allHalls = hallService.getAllHalls(pageable);
        return ResponseEntity.ok(allHalls);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createHall(@RequestBody HallDto hallDto) {
        Hall hall = hallService.createHall(hallDto);
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getHall(@PathVariable("id") long hallId) {
        Hall hall = hallService.getHall(hallId);
        return ResponseEntity.ok(hall);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> setHall(@PathVariable("id") long id, @RequestBody HallDto hallDto) {
        Hall hall = hallService.editHall(id, hallDto);
        return ResponseEntity.ok(hall);
    }
}
