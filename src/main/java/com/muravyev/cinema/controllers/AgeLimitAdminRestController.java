package com.muravyev.cinema.controllers;


import com.muravyev.cinema.dto.AgeLimitDto;
import com.muravyev.cinema.services.AgeLimitService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/admin/age")
public class AgeLimitAdminRestController {
    private final AgeLimitService ageLimitService;

    public AgeLimitAdminRestController(AgeLimitService ageLimitService) {
        this.ageLimitService = ageLimitService;
    }

    @PostMapping("/set")
    public ResponseEntity<?> setAgeLimit(@RequestBody AgeLimitDto ageLimitDto) {
        return ResponseEntity.ok(ageLimitService.setAgeLimit(ageLimitDto));
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deleteAgeLimit(@PathVariable("id") String id) {
        ageLimitService.deleteAgeLimit(id);
        return ResponseEntity.ok()
                .build();
    }

}
