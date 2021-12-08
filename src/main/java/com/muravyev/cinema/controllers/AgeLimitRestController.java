package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.services.AgeLimitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/age")
public class AgeLimitRestController {
    private final AgeLimitService limitService;

    public AgeLimitRestController(AgeLimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping(params = "id")
    public ResponseEntity<?> getAllLimits(@RequestParam("id") List<String> id) {
        log.info("Request AgeLimit: {}", id);
        List<AgeLimit> ageLimits = limitService.getAgeLimits(id);
        log.info("Get AgeLimit: {}", ageLimits);
        return ResponseEntity.ok(ageLimits);
    }

    @GetMapping
    public ResponseEntity<?> getLimits(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(limitService.getAgeLimits(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLimit(@PathVariable("id") String id) {
        return ResponseEntity.ok(limitService.getAgeLimits(List.of(id)).get(0));
    }
}
