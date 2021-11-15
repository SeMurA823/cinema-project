package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.services.AdminService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/admin")
public class AdminRestController {
    private AdminService adminService;

    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }


}
