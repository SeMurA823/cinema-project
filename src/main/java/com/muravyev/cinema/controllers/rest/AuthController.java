package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.security.services.token.Token;
import com.muravyev.cinema.security.services.token.cookieConfigurator.ClientSessionCookieConfigurator;
import com.muravyev.cinema.security.services.token.cookieConfigurator.CookieConfigurator;
import com.muravyev.cinema.security.services.token.cookieConfigurator.RefreshTokenCookieConfigurator;
import com.muravyev.cinema.security.services.token.manager.TokenManager;
import com.muravyev.cinema.security.services.token.manager.TokenPair;
import com.muravyev.cinema.security.services.token.manager.TokenPairClientable;
import com.muravyev.cinema.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final TokenManager tokenManager;
    private final RefreshTokenCookieConfigurator tokenCookieConfigurator;
    private final ClientSessionCookieConfigurator sessionCookieConfigurator;

    public AuthController(UserService userService,
                          TokenManager tokenManager,
                          RefreshTokenCookieConfigurator tokenCookieConfigurator,
                          ClientSessionCookieConfigurator sessionCookieConfigurator) {
        this.userService = userService;
        this.tokenManager = tokenManager;
        this.tokenCookieConfigurator = tokenCookieConfigurator;
        this.sessionCookieConfigurator = sessionCookieConfigurator;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDto registrationForm) {
        return ResponseEntity.ok(userService.registration(registrationForm));
    }

    @PostMapping(params = {"login"})
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        TokenPairClientable tokenPair = tokenManager.createTokenClientSession(user);
        String tokenCookie = tokenCookieConfigurator.configure(tokenPair.getRefreshToken());
        String sessionCookie = sessionCookieConfigurator.configure(tokenPair.getClient());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, tokenCookie)
                .header(HttpHeaders.SET_COOKIE, sessionCookie)
                .body(tokenPair.result());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@CookieValue("Refresh") String refreshToken, @RequestHeader("Authorization") String authorizationHeader) {
        TokenPair tokenPair = tokenManager.disable(refreshToken, authorizationHeader.substring(7));
        return ResponseEntity
                .ok()
                .body("Successfully");
    }

    @PostMapping(value = "/logout", params = {"all"})
    public ResponseEntity<?> logoutAll(@CookieValue("Refresh") String refreshToken, @CookieValue("ClientID") String clientID, Authentication authentication) {
        tokenManager.disableAll((User) authentication.getPrincipal());
        return ResponseEntity
                .ok("Successfully");
    }

    @PostMapping(params = {"refresh"})
    public ResponseEntity<?> profile(@CookieValue(name = "Refresh") Cookie cookie) {
        String refreshToken = cookie.getValue();
        TokenPair refreshedPair = tokenManager.refresh(refreshToken);
        String refreshTokenCookie = tokenCookieConfigurator.configure(refreshedPair.getRefreshToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body(refreshedPair.getAccessToken().result());
    }

    @PostMapping(params = {"refresh", "token"})
    public ResponseEntity<?> profile(@RequestParam("token") String refreshToken) {
        TokenPair tokenPair = tokenManager.
                refresh(refreshToken);
        return ResponseEntity.ok(tokenPair.result());
    }

}
