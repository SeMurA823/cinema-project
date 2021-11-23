package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.LoginDto;
import com.muravyev.cinema.dto.RegistrationDto;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.security.services.TokenManager;
import com.muravyev.cinema.security.services.TokenPair;
import com.muravyev.cinema.security.services.TokenPairClientable;
import com.muravyev.cinema.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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

    @PostMapping(params = {"login"})
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        User user = userService.login(loginDto);
        TokenPair tokenPair = tokenManager.createToken(user);
        ResponseCookie cookie = (loginDto.isRememberMe())
                ? tokenPair.getRefreshToken().toCookie()
                : tokenPair.getRefreshToken().toCookie(-1);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(tokenPair.getAccessToken().result());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@CookieValue("Refresh") String refreshToken, @CookieValue("ClientID") String clientID) {
        TokenPair tokenPair = tokenManager.disable(refreshToken, clientID);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, tokenPair.getRefreshToken().toCookie(0).toString())
                .body("Successfully");
    }

    @PostMapping(value = "/logout", params = {"all"})
    public ResponseEntity<?> logoutAll(@CookieValue("Refresh") String refreshToken, @CookieValue("ClientID") String clientID, Authentication authentication) {
        tokenManager.disableAll((User) authentication.getPrincipal());
        return ResponseEntity
                .ok("Successfully");
    }

//    @PostMapping(value = "/", params = {"refresh","refresh_token"})
//    public ResponseEntity<?> profile(@RequestParam("refresh_token") String refreshToken) {
//        return ResponseEntity.ok(tokenManager.refresh(refreshToken).toResult());
//    }

    @PostMapping(params = {"refresh"})
    public ResponseEntity<?> profile(@CookieValue("Refresh") Cookie cookie) {
        String refreshToken = cookie.getValue();
        TokenPair tokenPair = tokenManager.refresh(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        tokenPair.getRefreshToken()
                                .toCookie().toString())
                .body(tokenPair.getAccessToken().result());
    }

}