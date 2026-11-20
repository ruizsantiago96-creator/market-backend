package com.tecnm.merida.market_backend.domain.service;

import com.tecnm.merida.market_backend.domain.Client;
import com.tecnm.merida.market_backend.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAll() {
        return clientRepository.getAll();
    }

    public Optional<Client> getById(int clientId) {
        return clientRepository.getById(clientId);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> update(int clientId, Client client) {
        return clientRepository.getById(clientId)
                .map(existingClient -> {
                    existingClient.setFirstName(client.getFirstName());
                    existingClient.setLastName(client.getLastName());
                    existingClient.setCellPhone(client.getCellPhone());
                    existingClient.setAddress(client.getAddress());
                    existingClient.setEmail(client.getEmail());

                    return clientRepository.save(existingClient);
                });
    }

    public boolean delete(int clientId) {
        if (clientRepository.getById(clientId).isEmpty()) {
            return false;
        }

        clientRepository.delete(clientId);
        return true;
    }
}
