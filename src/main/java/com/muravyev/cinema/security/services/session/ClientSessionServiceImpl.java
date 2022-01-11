package com.muravyev.cinema.security.services.session;

import com.muravyev.cinema.entities.session.ClientSessionEntity;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.ClientSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClientSessionServiceImpl implements ClientSessionService {

    private final ClientSessionRepository clientSessionRepository;

    public ClientSessionServiceImpl(ClientSessionRepository clientRepository) {
        this.clientSessionRepository = clientRepository;
    }

    @Override
    public ClientSession createSession(User user) {
        ClientSessionEntity clientSession = new ClientSessionEntity();
        clientSession.setUser(user);
        return new ClientSessionImpl(clientSessionRepository.save(clientSession));
    }

    @Override
    @Transactional
    public void disableClient(String clientId) {
        clientSessionRepository.disableSessionById(UUID.fromString(clientId));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void disableAll(User user) {
        clientSessionRepository.disableAllSessionsByUser(user);
    }


    private static class ClientSessionImpl implements ClientSession {

        private final ClientSessionEntity clientSessionEntity;

        public ClientSessionImpl(ClientSessionEntity clientSessionEntity) {
            this.clientSessionEntity = clientSessionEntity;
        }

        @Override
        public String compact() {
            return clientSessionEntity.getId().toString();
        }

        @Override
        public String getSubject() {
            return clientSessionEntity.getUser().getUsername();
        }
    }


}
