package com.tecnm.merida.market_backend.persistence;

import com.tecnm.merida.market_backend.domain.Client;
import com.tecnm.merida.market_backend.domain.repository.ClientRepository;
import com.tecnm.merida.market_backend.persistence.crud.ClienteCrudRepository;
import com.tecnm.merida.market_backend.persistence.mapper.ClientMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class ClienteRepository implements ClientRepository {

    private final ClienteCrudRepository clienteCrudRepository;
    private final ClientMapper clientMapper;

    public ClienteRepository(ClienteCrudRepository clienteCrudRepository, ClientMapper clientMapper) {
        this.clienteCrudRepository = clienteCrudRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public List<Client> getAll() {
        return StreamSupport.stream(clienteCrudRepository.findAll().spliterator(), false)
                .map(clientMapper::toClient)
                .toList();
    }

    @Override
    public Optional<Client> getById(int clientId) {
        return clienteCrudRepository.findById(clientId)
                .map(clientMapper::toClient);
    }

    @Override
    public Client save(Client client) {
        return clientMapper.toClient(clienteCrudRepository.save(clientMapper.toCliente(client)));
    }

    @Override
    public void delete(int clientId) {
        clienteCrudRepository.deleteById(clientId);
    }
}
