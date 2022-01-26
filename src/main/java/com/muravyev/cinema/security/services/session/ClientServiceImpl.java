package com.muravyev.cinema.security.services.session;

import com.muravyev.cinema.entities.session.ClientEntity;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client createSession(User user) {
        ClientEntity clientSession = new ClientEntity();
        clientSession.setUser(user);
        return new ClientImpl(clientRepository.save(clientSession));
    }

    @Override
    @Transactional
    public void disableClient(String clientId) {
        clientRepository.disableSessionById(UUID.fromString(clientId));
    }

    @Override
    @Transactional
    public void disableAll(User user) {
        clientRepository.disableAllSessionsByUser(user);
    }


    private static class ClientImpl implements Client {

        private final ClientEntity clientEntity;

        public ClientImpl(ClientEntity clientEntity) {
            this.clientEntity = clientEntity;
        }

        @Override
        public String compact() {
            return clientEntity.getId().toString();
        }

        @Override
        public String getSubject() {
            return clientEntity.getUser().getUsername();
        }
    }


}
