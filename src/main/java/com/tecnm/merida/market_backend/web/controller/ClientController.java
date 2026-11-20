package com.tecnm.merida.market_backend.web.controller;

import com.tecnm.merida.market_backend.domain.Client;
import com.tecnm.merida.market_backend.domain.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAll() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getById(@PathVariable String id) {
        return clientService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> save(@RequestBody Client client) {
        String clientId = client.getClientId();
        if (clientId == null || clientId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (clientService.getById(clientId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable String id, @RequestBody Client client) {
        return clientService.update(id, client)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!clientService.delete(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
