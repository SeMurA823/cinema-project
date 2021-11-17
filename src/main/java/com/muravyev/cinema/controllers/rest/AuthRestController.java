package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.security.services.TokenManager;
import com.muravyev.cinema.security.services.TokenPair;
import com.muravyev.cinema.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private UserService userService;
    private TokenManager tokenManager;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDto registrationForm) {
        return ResponseEntity.ok(userService.registration(registrationForm));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        if (loginDto.getUsername().equals("token_auth")) {
            return loginByToken(loginDto);
        }
        return loginByUsernameAndPassword(loginDto);
    }

    private ResponseEntity<?> loginByUsernameAndPassword(LoginDto loginDto) {
        User user = userService.login(loginDto);
        TokenPair tokenPair = tokenManager.createToken(user);
        return ResponseEntity.ok(tokenPair);
    }

    private ResponseEntity<?> loginByToken(LoginDto loginDto) {
        log.info("Refresh token: {}", loginDto.getPassword());
        String refreshTokenStr = loginDto.getPassword();
        return ResponseEntity.ok(tokenManager.refresh(refreshTokenStr));
    }
}
