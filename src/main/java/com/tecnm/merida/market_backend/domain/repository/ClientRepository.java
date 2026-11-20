package com.tecnm.merida.market_backend.domain.repository;

import com.tecnm.merida.market_backend.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    List<Client> getAll();

    Optional<Client> getById(int clientId);

    Client save(Client client);

    void delete(int clientId);
}
