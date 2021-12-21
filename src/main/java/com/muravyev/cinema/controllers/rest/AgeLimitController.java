package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.services.AgeLimitService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{id}")
    public ResponseEntity<?> getLimit(@PathVariable("id") String id) {
        return ResponseEntity.ok(limitService.getAgeLimits(List.of(id)).get(0));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{ageLimit}")
    public ResponseEntity<?> setAgeLimit(@PathVariable("ageLimit") String ageLimit, @RequestBody AgeLimitDto ageLimitDto) {
        return ResponseEntity.ok(limitService.setAgeLimits(List.of(ageLimit), ageLimitDto).get(0));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> setAgeLimits(@RequestParam("id") List<String> id, @RequestBody AgeLimitDto ageLimitDto) {
        return ResponseEntity.ok(limitService.setAgeLimits(id, ageLimitDto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> addAgeLimit(@RequestBody AgeLimitDto ageLimitDto) {
        return setAgeLimit(ageLimitDto.getName(), ageLimitDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAgeLimit(@PathVariable("id") String id) {
        limitService.deleteAgeLimits(List.of(id));
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteAgeLimits(@RequestParam("id") List<String> id) {
        limitService.deleteAgeLimits(id);
        return ResponseEntity.ok().build();
    }
}
