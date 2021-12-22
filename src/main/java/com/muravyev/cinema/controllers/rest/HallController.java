package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.HallDto;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.services.HallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/admin/halls", "/api/halls"})
public class HallController {
    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllHalls(@PageableDefault Pageable pageable) {
        Page<Hall> allHalls = hallService.getAllHalls(pageable);
        return ResponseEntity.ok(allHalls);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createHall(@RequestBody HallDto hallDto) {
        Hall hall = hallService.createHall(hallDto);
        return ResponseEntity.ok(hall);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "{hall}/seats/create")
    public ResponseEntity<?> addSeat(@PathVariable("hall") long hallId,
                                     @RequestParam("row") int row) {
        Seat seat = hallService.addSeat(hallId, row);
        return ResponseEntity.ok(seat);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "{hall}/seats", params = "id")
    public ResponseEntity<?> deleteSeats(@PathVariable("hall") long hallId,
                                         @RequestParam("id") List<Long> seatIds) {
        hallService.deleteSeats(hallId, seatIds);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "{hall}/seats", params = {"id", "used"})
    public ResponseEntity<?> setStatusSeats(@PathVariable("hall") long hallId,
                                            @RequestParam("id") List<Long> seatIds,
                                            @RequestParam("used") boolean used) {
        hallService.setUnUsedSeats(hallId, seatIds, !used);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "{hall}/seats", params = {"row", "size"})
    public ResponseEntity<?> addSeats(@PathVariable("hall") long hallId,
                                      @RequestParam("row") int row,
                                      @RequestParam("size") int size) {
        List<Seat> seats = hallService.addSeats(hallId, row, size);
        return ResponseEntity.ok(seats);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{hall}/seats")
    public ResponseEntity<?> getSeats(@PathVariable("hall") long hallId) {
        Map<Integer, List<Seat>> seats = hallService.getAllSeats(hallId);
        return ResponseEntity.ok(seats.values());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getHall(@PathVariable("id") long hallId) {
        Hall hall = hallService.getHall(hallId);
        return ResponseEntity.ok(hall);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<?> setHall(@PathVariable("id") long id, @RequestBody HallDto hallDto) {
        Hall hall = hallService.editHall(id, hallDto);
        return ResponseEntity.ok(hall);
    }

    @GetMapping(params = "search")
    public ResponseEntity<?> getHall(@RequestParam("search") String search, @PageableDefault Pageable pageable) {
        Page<Hall> halls = hallService.getHalls(search, pageable);
        return ResponseEntity.ok(halls);
    }
}
