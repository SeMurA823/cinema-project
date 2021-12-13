package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.HallDto;
import com.muravyev.cinema.entities.IdentityBaseEntity;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.services.HallService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/hall")
public class HallAdminRestController {
    private final HallService hallService;

    public HallAdminRestController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping("/create")
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

    @GetMapping
    public ResponseEntity<?> getHallsDto(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(hallService.getAllHalls(pageable).map(this::from));
    }

    private HallDto from(Hall hall) {
        HallDto hallDto = new HallDto();
        hallDto.setId(hall.getId());
        hallDto.setName(hall.getName());
        hallDto.setSeatsId(hall.getSeats().stream()
                .map(IdentityBaseEntity::getId)
                .collect(Collectors.toSet()));
        return hallDto;
    }
}
