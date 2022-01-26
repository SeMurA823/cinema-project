package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.services.AgeLimitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping({"/api/age", "/api/admin/age"})
public class AgeLimitController {
    private final AgeLimitService limitService;

    public AgeLimitController(AgeLimitService limitService) {
        this.limitService = limitService;
    }

    @GetMapping(params = "id")
    public ResponseEntity<?> getLimits(@RequestParam("id") List<String> id) {
        log.info("Request AgeLimit: {}", id);
        List<AgeLimit> ageLimits = limitService.getAgeLimits(id);
        log.info("Get AgeLimit: {}", ageLimits);
        return ResponseEntity.ok(ageLimits);
    }

    @GetMapping
    public ResponseEntity<Iterable<?>> getAllLimits() {
        return ResponseEntity.ok(limitService.getAgeLimits());
    }
}
