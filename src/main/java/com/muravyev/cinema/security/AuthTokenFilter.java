package com.muravyev.cinema.security;

import com.muravyev.cinema.security.services.token.AccessTokenService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
@Log4j2
public class AuthTokenFilter extends GenericFilterBean {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private UserDetailsService userDetailsService;


    private Optional<String> getTokenRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return Optional.of(authorization.substring(7));
        }
        return Optional.empty();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            log.debug("Using filter: {}", this.getClass().getName());
            Optional<String> accessToken = getTokenRequest((HttpServletRequest) servletRequest);
            if (accessToken.isPresent()) {
                log.debug("Access token: {}", accessToken.get());
                String username = accessTokenService.extractUsername(accessToken.get());
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error(e);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
