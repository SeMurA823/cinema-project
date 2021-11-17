package com.muravyev.cinema.security.services;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.users.RefreshToken;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.RefreshTokenRepository;
import com.muravyev.cinema.security.exceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenUtils {
    @Value("${token.refresh.expiration}")
    private long expiration;

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Transactional
    public String createToken(User user) {
        Optional<RefreshToken> tokenOptional = tokenRepository.findByUser(user);
        RefreshToken refreshToken = tokenOptional.orElse(new RefreshToken());
        refreshToken.setToken(generateStringToken(user));
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(getExpiryDate());
        RefreshToken rt = tokenRepository.save(refreshToken);
        return rt.getToken();
    }

    public String extractUsername(String token) {
        byte[] decode = Base64.getDecoder().decode(token);
        String tokenDecode = new String(decode);
        return tokenDecode.split(":")[1];
    }

    public Date extractExpiration(String token) {
        return getEntity(token).getExpiryDate();
    }

    public boolean isExpiredToken(String token) {
        return extractExpiration(token).after(new Date());
    }

    @Transactional
    public String refreshToken(String token) {
        RefreshToken refreshToken = getEntity(token);
        refreshToken.setToken(generateStringToken(refreshToken.getUser()));
        refreshToken.setExpiryDate(getExpiryDate());
        return tokenRepository.save(refreshToken).getToken();
    }

    private Date getExpiryDate() {
        Date now = new Date();
        return new Date(now.getTime() + expiration);
    }

    private RefreshToken getEntity(String token) {
        return tokenRepository.findByTokenAndEntityStatus(token, EntityStatus.ACTIVE)
                .orElseThrow(InvalidTokenException::new);
    }

    private String generateStringToken(UserDetails userDetails) {
        String uuid = UUID.randomUUID().toString() + ':' + userDetails.getUsername();
        byte[] uuidEncode = Base64.getEncoder().encode(uuid.getBytes(StandardCharsets.UTF_8));
        return new String(uuidEncode);
    }
}
