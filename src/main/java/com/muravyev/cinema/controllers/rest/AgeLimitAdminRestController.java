package com.muravyev.cinema.controllers.rest;


import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.services.AgeLimitService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/admin/age")
public class AgeLimitAdminRestController {
    private final AgeLimitService ageLimitService;

    public AgeLimitAdminRestController(AgeLimitService ageLimitService) {
        this.ageLimitService = ageLimitService;
    }

    @PutMapping("/put")
    public ResponseEntity<?> setAgeLimit(@RequestBody AgeLimitDto ageLimitDto) {
        return ResponseEntity.ok(ageLimitService.setAgeLimit(ageLimitDto));
    }

}
