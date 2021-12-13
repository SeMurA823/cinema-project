package com.muravyev.cinema.controllers;


import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.services.AgeLimitService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/admin/age")
public class AgeLimitAdminRestController {
    private final AgeLimitService ageLimitService;

    public AgeLimitAdminRestController(AgeLimitService ageLimitService) {
        this.ageLimitService = ageLimitService;
    }

    @PostMapping("/{ageLimit}")
    public ResponseEntity<?> setAgeLimit(@PathVariable("ageLimit") String ageLimit, @RequestBody AgeLimitDto ageLimitDto) {
        return ResponseEntity.ok(ageLimitService.setAgeLimits(List.of(ageLimit), ageLimitDto).get(0));
    }

    @PostMapping
    public ResponseEntity<?> setAgeLimits(@RequestParam("id") List<String> id, @RequestBody AgeLimitDto ageLimitDto) {
        return ResponseEntity.ok(ageLimitService.setAgeLimits(id, ageLimitDto));
    }

    @PostMapping("/create")
    public ResponseEntity<?> addAgeLimit(@RequestBody AgeLimitDto ageLimitDto) {
        return setAgeLimit(ageLimitDto.getName(), ageLimitDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAgeLimit(@PathVariable("id") String id) {
        ageLimitService.deleteAgeLimits(List.of(id));
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAgeLimits(@RequestParam("id") List<String> id) {
        ageLimitService.deleteAgeLimits(id);
        return ResponseEntity.ok().build();
    }

}
