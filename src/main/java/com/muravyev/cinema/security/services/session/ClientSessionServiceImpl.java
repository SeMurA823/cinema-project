package com.muravyev.cinema.security.services.session;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.session.ClientSession;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.ClientSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class ClientSessionServiceImpl implements ClientSessionService<ClientSession> {

    @Value("${session.client.cookie}")
    private String cookieName;

    @Value("${app.cookie.path}")
    private String cookiePath;

    @Value("${app.cookie.domain}")
    private String cookieDomain;

    private final ClientSessionRepository clientSessionRepository;

    public ClientSessionServiceImpl(ClientSessionRepository clientRepository) {
        this.clientSessionRepository = clientRepository;
    }

    @Override
    public HttpClientSessionable<ClientSession> createSession(User user) {
        ClientSession clientSession = new ClientSession();
        clientSession.setUser(user);
        return new HttpClientSessionImpl(clientSessionRepository.save(clientSession));
    }

    @Override
    @Transactional
    public void disableClient(String clientId) {
        ClientSession clientSession = clientSessionRepository.findById(UUID.fromString(clientId))
                .orElseThrow(EntityNotFoundException::new);
        clientSession.setEntityStatus(EntityStatus.NOT_ACTIVE);
    }

    //TODO: set level isolation
    @Override
    @Transactional
    public void disableAll(User user) {
        List<ClientSession> clientSessions =
                clientSessionRepository.findAllByUserAndEntityStatus(user, EntityStatus.ACTIVE);
        clientSessions.forEach(x -> x.setEntityStatus(EntityStatus.NOT_ACTIVE));
        clientSessionRepository.saveAll(clientSessions);
    }

    private class HttpClientSessionImpl implements HttpClientSessionable<ClientSession> {
        private final ClientSession clientSession;

        public HttpClientSessionImpl(ClientSession clientSession) {
            this.clientSession = clientSession;
        }

        @Override
        public ResponseCookie toCookie(long maxAge) {
            return ResponseCookie.from(cookieName, clientSession.getId().toString())
                    .path(cookiePath)
                    .domain(cookieDomain)
                    .maxAge(maxAge)
                    .httpOnly(true)
                    .sameSite("LAX")
                    .build();
        }

        @Override
        public String compact() {
            return clientSession.getId().toString();
        }

        @Override
        public ClientSession get() {
            ClientSession clone = new ClientSession();
            clone.setName(clientSession.getName());
            clone.setId(clientSession.getId());
            clone.setUser(clientSession.getUser());
            clone.setEntityStatus(clientSession.getEntityStatus());
            clone.setIpv4(clientSession.getIpv4());
            clone.setIpv6(clientSession.getIpv6());
            clone.setCreated(clientSession.getCreated());
            clone.setUpdated(clientSession.getUpdated());
            return clone;
        }
    }


}
